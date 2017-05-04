@Grab('io.github.http-builder-ng:http-builder-ng-core:0.15.0')
import groovyx.net.http.*

import groovy.json.*

println "Starting"

String path = "target/Discriminators.java"

String github = "https://api.github.com"
String owner = "lapps"
String repository = 'org.lappsgrid.discriminator'
String branch = 'testing'

String token = new File('/Users/suderman/.secret/github.token').text
println "Token $token"
def parser = new JsonSlurper()

// Find the HEAD of the select branch
String url = "$github/repos/$owner/$repository/git/refs/heads/$branch"

def http = HttpBuilder.configure {
	request.uri = github
}

/*
 * Step 1. Get a reference to HEAD
 */
println "Getting HEAD"
def head = http_get(url)
pretty head
String sha_latest_commit = head.object.sha

/*
 * Step 2. Get the commit that HEAD points to.
 */
// Get the commit object that HEAD points to.
println "Getting commit object"
def commit = http_get(head.object.url)
pretty commit
String sha_base_tree = commit.tree.sha

/*
 * Step 3. POST the file to GitHub.
 *
println "Posting new file to GitHub"

// The data to be POSTed to GitHub
String java = new File(path).text
def data = [
	content: java.bytes.encodeBase64().toString(),
	encoding: 'base64'
]

def blob = http.post(Map) {
	request.uri.path = "/repos/$owner/$repository/git/blobs".toString()
	request.headers['Authorization'] = "token $token".toString()
	request.contentType = 'application/json'
	request.body = data
}

pretty blob
*/

String responseJson = '''{
    "sha": "94341c81b6c375439c32ff9a6415264f73666bb1",
    "url": "https://api.github.com/repos/lapps/org.lappsgrid.discriminator/git/blobs/94341c81b6c375439c32ff9a6415264f73666bb1"
}'''

def blob = parser.parseText(responseJson)
String sha_new_tree = blob.sha
//pretty blob

/*
 * Step 4. Get the tree the commit points to.
 */
println "Getting the tree for the latest commit"
def tree = http_get(commit.tree.url)
//pretty tree
println "Got ${tree.sha}"

// Create a new tree for our file.
/*
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
pretty entity

Map newTree = http.post(Map) {
	request.uri.path = "/repos/$owner/$repository/git/trees".toString()
	request.headers['Authorization'] = "token $token".toString()
	request.contentType = 'application/json'
	request.body = entity
}
pretty newTree
*/

Map newTree = parser.parseText '''{
    "author": {
        "date": "2017-05-02T01:49:55Z",
        "email": "suderman@cs.vassar.edu",
        "name": "Keith Suderman"
    },
    "committer": {
        "date": "2017-05-02T01:49:55Z",
        "email": "suderman@cs.vassar.edu",
        "name": "Keith Suderman"
    },
    "html_url": "https://github.com/lapps/org.lappsgrid.discriminator/commit/7e9586445816407e793d41d1d18d42cee317edb6",
    "message": "New Discriminators after vocabulary build.",
    "parents": [
        {
            "html_url": "https://github.com/lapps/org.lappsgrid.discriminator/commit/74115ef1f1c6e874fe1951c9159487b4f30f6141",
            "sha": "74115ef1f1c6e874fe1951c9159487b4f30f6141",
            "url": "https://api.github.com/repos/lapps/org.lappsgrid.discriminator/git/commits/74115ef1f1c6e874fe1951c9159487b4f30f6141"
        }
    ],
    "sha": "7e9586445816407e793d41d1d18d42cee317edb6",
    "tree": {
        "sha": "e8d69c04ae6e906bf744622758eac87cfd963517",
        "url": "https://api.github.com/repos/lapps/org.lappsgrid.discriminator/git/trees/e8d69c04ae6e906bf744622758eac87cfd963517"
    },
    "url": "https://api.github.com/repos/lapps/org.lappsgrid.discriminator/git/commits/7e9586445816407e793d41d1d18d42cee317edb6"
}'''

// Create a new commit containing the new tree we just created.
data = [
	message: 'New Discriminators after vocabulary build.',
	parents: [ sha_latest_commit ],
	tree: sha_new_tree
]

println "Creating a new commit."
pretty data
def newCommit = http.post(Map) {
	request.uri.path = "/repos/$owner/$repository/git/commits".toString()
	request.headers['Authorization'] = "token $token".toString()
	request.contentType = 'application/json'
	request.body = data
}
pretty newCommit

// Update HEAD to point to the new commit
println "Updating HEAD"
def update = http.post(Map) {
	request.uri.path = "/repos/$owner/$repository/git/refs/$branch".toString()
	request.headers['Authorization'] = "token $token".toString()
	request.contentType = 'application/json'
	request.body = [
		sha: newCommit.sha,
		force: true
	]
}
println "Update:"
pretty update

return

void pretty(Object object) {
	println new JsonBuilder(object).toPrettyString()
}

Map http_get(String u) {
	String json = new URL(u).text
	new JsonSlurper().parseText(json)
}
