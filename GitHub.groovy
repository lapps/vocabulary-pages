@Grab('io.github.http-builder-ng:http-builder-ng-core:0.15.0')

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovyx.net.http.HttpBuilder
import groovyx.net.http.HttpException

import static groovyx.net.http.ContentTypes.*

/**
 * @author Keith Suderman
 */
class GitHub {

    private JsonSlurper parser
    private HttpBuilder http
    String token
    String owner
    String repo

    public GitHub() {
        parser = new JsonSlurper();
        http = HttpBuilder.configure {
            request.uri = "https://api.github.com"
        }
    }

    public void configure(Map params) {
        this.token = params.token
        this.owner = params.owner
        this.repo = params.repo
    }

    public void commit(File file, String path, String branch) {
        println "Committing ${file.path} to ${master}:${path}"

        /*
         * Step 1. Get a reference to HEAD
         */
        println "Getting HEAD"
        def head = get("git/refs/heads/$branch")
        prettyPrint head
        String sha_latest_commit = head.object.sha

        /*
         * Step 2. Get the commit that HEAD points to.
         */
        println "Getting commit object"
        def commit = get(head.object.url)
        prettyPrint commit
        String sha_base_tree = commit.tree.sha

        /*
         * Step 3. POST the file to GitHub.
         */
        println "Posting new file to GitHub"

        // The data to be POSTed to GitHub
        String java = file.text
        def data = [
            content: java.bytes.encodeBase64().toString(),
            encoding: 'base64'
        ]

        def blob = post("git/blobs", data)
        prettyPrint blob

        /*
         * Step 4. Get the tree the commit points to.
         */
        println "Getting the tree for the latest commit"
        def tree = get(commit.tree.url)
        //prettyPrint tree
        println "Got ${tree.sha}"

        /*
         * Step 5. Create a new tree for our file.
         */
        println "Creating a new tree."
        def entity = [
            base_tree: commit.tree.sha,
            tree: [
                [
                    //path: '/src/main/java/org/lappsgrid/discriminator/Discriminators.java',
                    path: 'Discriminators.java',
                    mode: '100644',
                    type: 'blob',
                    sha: blob.sha
                ]
            ]
        ]
        prettyPrint entity

        Map newTree = post("git/trees", entity)
        prettyPrint newTree


        /*
         * Step 6. Create a new commit containing the tree we just created.
         */
        data = [
                message: 'New Discriminators after vocabulary build.',
                parents: [ sha_latest_commit ],
                tree: newTree.sha
        ]

        println "Creating a new commit."
        prettyPrint data
        def newCommit = post("git/commits", data)
        prettyPrint newCommit

        /*
         * Step 7. Update HEAD to point to the new commit
         */
        println "Updating HEAD"
        data = [
                sha: newCommit.sha,
                force: true
        ]
        def update = post("git/refs/$branch", data)
        println "Update:"
        prettyPrint update

        println "Commit complete."
    }


    void prettyPrint(Object object) {
        println new JsonBuilder(object).toPrettyString()
    }

    Map post(String path, Map data) {
        return http.post(Map) {
            request.uri.path = makePath(path)
            //request.accept = [ JSON ] as String[]
            request.contentType = JSON
            request.headers.Authorization = "token $token"
            request.body = data
        }
    }

    Map get(String path) {
        if (path.startsWith('http')) {
            String json = new URL(path).text
            return parser.parseText(json)
        }

        return http.get(Map) {
            request.uri.path = makePath(path)
            request.accept = [ JSON ] as String[]

            response.failure {
                println "Failed to get $path"
            }
        }
    }

    String makePath(String path) {
        return "/repos/$owner/$repo/$path".toString()
    }

	public static void main(String[] args) {
        String token = new File("/Users/suderman/.secret/github.token").text
        println "Token: $token"
        Map config = [
                token:token,
                owner:'lappsgrid-incubator',
                repo:'Testing'
        ]

        GitHub github = new GitHub()
        github.configure(config)

        File java = new File('/Users/suderman/Workspaces/IntelliJ/Lappsgrid/vocab/target/Annotations.java')

        github.commit(java, "src/main/java/org/lappsgrid/vocabulary/Annotations.java", "master")
    }
}
