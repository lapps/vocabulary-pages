/*
 * This template expects three parameters to be passed to it:
 *
 * 1. element : the ElementDelegate for the page being generated.
 * 2. elements: a HashMap used to map element names to their ElementDelegate object.
 * 3. parents: the names all parents of this element.
 */
html {
    head {
        title element.name
        link rel:'stylesheet', type:'text/css', href:'lappsstyle.css'
    }
    body {
        div(id:'container') {
            div(id:'intro') {
                div(id:'pageHeader') {
                    h1 "LAPPS Web Service Exchange Vocabulary"
                }
            }
            div(id:'mainContent') {
                div(id:'sectionbar') {
                    p {
                        a(href:'index.html', 'Home')
                    }
                }
                p(class:'head') {
                    parents.each { parent ->
                        a(href:"${parent.name}.html", parent.name)
                        span ' > '
                    }
                    span element.name
                }
                br()
                table {
                    tr {
                        td(class:'definition') { b 'Definition' }
                        td element.definition
                    }
                    if (element.sameAs.size() > 0) {
                        tr {
                            td { b "Same as" }
                            td {
                                element.sameAs.collect { a(href:it, it) }.join(", ")
                            }
                        }
                    }
                    if (element.similarTo.size() > 0) {
                        tr {
                            td { b "Similar to" }
                            td {
                                element.similarTo.collect { a(href:it, it) }.join(", ")
                            }
                        }
                    }
                    tr {
                        td { b "URI" }
                        //td element.uri
                        td "http://vocab.lappsgrid.org/${element.name}"
                    }
                }

                boolean headline = true
                def node = element
                while (node) {
                    if (node.metadata.size() > 0) {
                        if (headline) {
                            // The headline only gets printed if there are metadata attributes defined.
                            h2 "Metadata"
                            headline = false
                        }
                        if (node.name != element.name) {
                            h3 {
                                span "Metadata from "
                                a(href: "${node.name}.html", node.name)
                            }
                        }
                        table(class: 'definition-table') {
                            tr {
                                th class:'fixed', "Properties"
                                th class:'fixed', "Type"
                                th "Description"
                            }
                            List names = node.metadata.keySet().asList()
                            names.each { name ->
                                tr {
                                    def property = node.metadata[name]
                                    td name
                                    td property.type
                                    td {
                                        mkp.yieldUnescaped property.description
                                    }
                                }
                            }
                        }
                    }
                    node = elements[node.parent]
                }

                headline = true
                node = element
                while (node) {
                    if (node.properties.size() > 0) {
                        if (headline) {
                            h2 'Properties'
                            headline = false
                        }
                        if (node.name != element.name) {
                            h3 {
                                span "Properties from "
                                a(href:"${node.name}.html", node.name)
                            }
                        }
                        table(class: 'definition-table') {
                            tr {
                                th class:'fixed', "Properties"
                                th class:'fixed', "Type"
                                th "Description"
                            }
                            List names = node.properties.keySet().asList()
                            names.each { name ->
                                tr {
                                    def property = node.properties[name]
                                    td name
                                    td property.type
                                    td {
                                        mkp.yieldUnescaped property.description
                                    }
                                }
                            }
                        }
                    }
                    node = elements[node.parent]
                }
                br()
                div(class:'index') {
                    span "Back to the "
                    a(href:'index.html', 'index')
                }
            }
        }
        div(id:'mainContent') {
            /****
             p "These are the annotation types defined in the LAPPS vocabulary."
             p """Note that LAPPS does not define any types of its own.  The LAPPS
             vocabulary simply enumerates the URI of all types used by LAPPS
             services. LAPPS services may use other types with different URI and
             different types.  However, all LAPPS services SHOULD recognize at least
             the annotation types listed here."""
             ****/
            p """The LAPPS Web Service Exchange Vocabulary defines an ontology of
                 terms for a core of linguistic objects and features exchanged among
                 NLP tools that consume and produce linguistically annotated data.
                 It is intended to be used for module description and input/output
                 interchange to support service discovery, composition, and reuse in
                 the natural language processing domain."""

            p """The Exchange Vocabulary is being developed bottom-up on an as-needed
                 basis for use in the development of the LAPPS Grid. The Type
                 Hierarchy below contains all of the elements for which specifications
                 have been finalized so far. Detailed information is available by
                 clicking on the relevant element."""
            //p "TODO: The RFC that defines SHOULD should be listed here."
//                p """Note that LAPPS does not define any types of its own.  The LAPPS
//                    vocabulary simply enumerates the URI of all types used by LAPPS
//                    services. LAPPS services may use other types with different URI and
//                    different types.  However, all LAPPS services SHOULD recognize at least
//                    the annotation types listed here."""
            br()
            h1 "LAPPS Exchange Vocabulary Type Hierarchy"
            roots.each { root ->
                //ul(class:'tree') {
                //    printNode(root)
                //}
                br()
                printTable(root)
            }
        }
		def year = Calendar.getInstance().get(Calendar.YEAR)
		mkp.yieldUnescaped "<div id='footer'>Copyright &copy; ${year} The Language Application Grid. All rights reserved.</div>"
        div(id:'footer', 'Page generated by the MarkupBuilderTemplateEngine.')
    }
}
/**
 * Created by Nathan on 8/1/2014.
 */
