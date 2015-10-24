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
                        td(class:'fixed') { b 'Definition' }
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
                        td element.uri
                    }
                }

                def node = element
                if (node.metadata.size() > 0) {
                	h2 'Metadata'
                	table(class:'definition-table') {
						tr {
							th class:'fixed', "Properties"
							th class:'fixed', "Type"
							th "Description"
						}
						boolean headline = false
						while (node) {
							if (headline && node.metadata.size() > 0) {
								tr {
									th(colspan:3) {
										a(href:"${node.name}.html", node.name)
									}
								}
							}
							headline = true
							node.metadata.each { name,property ->
								tr {
									td name
									td property.type
									td {
										mkp.yieldUnescaped property.description
									}
								}
							}
							node = elements[node.parent]
						}
                	}
                }
                /* h1 "Properties" */
                node = element
                if (node.properties.size() > 0) {
                	h2 'Properties'
                	table(class:'definition-table') {
						tr {
							th class:'fixed', "Properties"
							th class:'fixed', "Type"
							th "Description"
						}
						boolean headline = false
						while (node) {
							if (headline && node.properties.size() > 0) {
								tr {
									th(colspan:3) {
										a(href:"${node.name}.html", node.name)
									}
								}
							}
							headline = true
							node.properties.each { name,property ->
								tr {
									td name
									td property.type
									td {
										mkp.yieldUnescaped property.description
									}
								}
							}
							node = elements[node.parent]
						}
                	}
                }
                br()
                div(class:'index') {
                    span "Back to the "
                    a(href:'index.html', 'index')
                }
            }
        }
        div(id:'footer', 'Page generated by the MarkupBuilderTemplateEngine.')
    }
}
/**
 * Created by Nathan on 8/1/2014.
 */