# Generating the LAPPS Grid Vocabulary

This project contains the vocabulary definition (DSL) and templates used to generate the [LAPPS Grid vocabulary](http://vocab.lappsgrid.org) website as well as Java code for the [Discriminator](https://github.com/lapps/org.lappsgrid.discriminator) and [Vocabulary](https://github.com/lapps/org.lappsgrid.vocabulary) modules.

## Prerequisites

The latest versions of following programs should be available in the `bin/` directory.  

**Vocabulary DSL**<br/>The vocabulary DSL is used to generate the web pages for vocabulary items as well as the Java class for the vocabulary Java API classes. The vocabulary DSL also generates the <tt>vocabulary.config</tt> file that is included by the <tt>discriminators.config</tt>.

[Download](http://www.anc.org/downloads/vocab-latest.tgz)

**Discriminator DSL**<br/>The discriminator DSL is used to generated the discriminator web pages for the vocab site as will as the Discriminators Java class for the `org.lappsgrid.discriminator` module.

[Download](http://www.anc.org/downloads/discriminator-latest.tgz)

## Makefile

A Makefile is available to automate most of the tasks involved in generating a new vocabulary web site and related Java classes.  To generate everything simply run:

```bash
make all
```


### Goals

<dl>
<dt>vocabulary</dt>
<dd>Generates the <tt>vocabulary.config</tt> file used by the discriminator dsl</dd>

<dt>html</dt>
<dd>Generates all html pages for the http:vocab.lappsgrid.org web site.</dd>

<dt>java</dt>
<dd>Generates the Java source files for the Discriminators, Vocabulary, Annotations and Features classes.</dd>

<dt>rdf</dt>
<dd>Generates the vocabulary in all the RDF(-like) formats: rdf, own, ttl, and json-ld.</dd>

<dt>all</dt>
<dd>Does all of the above</dd>

<dt>clean</dt>
<dd>Removes all artifacts from previous builds.</dd>

<dt>help</dt>
<dd>Displays a simply help message.</dd>

</dl>

There is a goal to upload the HTML files to the http://vocab.lappsgrid.org site, but the goal is not portable and must be tailored for each person deploying to the server.  In particular, the person deploying the site must have write permission for the appropriate directories.

There is also a goal to copy the generated Java files to the vocabulary and discriminator projects.  But these are also not portable and must be specially tailored for each person.

## Further Reading

Please see the [LAPPS Grid Wiki](http://wiki.lappsgrid.org/technical/discriminators) for more information.

