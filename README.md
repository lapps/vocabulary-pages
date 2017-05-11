# Generating the LAPPS Grid Vocabulary

This project contains the vocabulary definition (DSL) and templates used to generate the [LAPPS Grid vocabulary](http://vocab.lappsgrid.org) website as well as Java code for the [Discriminator](https://github.com/lapps/org.lappsgrid.discriminator) and [Vocabulary](https://github.com/lapps/org.lappsgrid.vocabulary) modules.

## Prerequisites

The latest versions of following programs should be available in the `bin/` directory.  

**Vocabulary DSL**<br/>
The vocabulary DSL is used to generate the web pages for vocabulary items as well as the Java class for the vocabulary Java API classes. The vocabulary DSL also generates the <tt>vocabulary.config</tt> file that is included by the <tt>discriminators.config</tt> file.<br/>
[Download](http://www.anc.org/downloads/vocab-latest.tgz)

**Discriminator DSL** <br/>
The discriminator DSL is used to generated the discriminator web pages for the vocab site as will as the Discriminators Java class for the `org.lappsgrid.discriminator` module.<br/>
[Download](http://www.anc.org/downloads/discriminator-latest.tgz)


**GitHub Commit**<br/>
The GitHub Commit (ghc) program is a command line program used to commit files to a GitHub repository and create pull requests.<br/>
[Download](http://www.anc.org/downloads/ghc-latest.tgz)


## Makefile

A Makefile is available to automate most of the tasks involved in generating a new vocabulary web site and related Java classes.  To generate everything simply run:

```bash
make all
```

This uses the Vocabulary DSL and the Discriminator DSL and runs them on two user-generated files: `lapps.vocabulary` which has the annotation types for the LAPPS vocabulary and `lapps.discriminators` which has the discriminators.

### Goals

<dl>
<dt>vocabulary</dt>
<dd>Generates from <tt>lapps.vocabulary</tt> the <tt>target/vocabulary.config</tt> file used by the discriminator dsl</dd>

<dt>html</dt>
<dd>Generates all html pages for the http:vocab.lappsgrid.org web site. All those pages as well as <tt>css</tt> and <tt>js</tt> directories are written to <tt>target</tt>. </dd>

<dt>java</dt>
<dd>Generates Java source files for the vocabulary package (https://github.com/lapps/org.lappsgrid.vocabulary) and the discriminators package (https://github.com/lapps/org.lappsgrid.discriminator). The Java classes created (<tt>Annotations.java</tt>, <tt>Features.java</tt> and <tt>Discriminators.java</tt>) are all in the <tt>target</tt> directory.</dd>

<dt>rdf</dt>
<dd>Generates the vocabulary in all the RDF(-like) formats: rdf, owl, ttl, and json-ld. Files created are
<tt>target/lapps-vocabulary.jsonld</tt>, <tt>target/lapps-vocabulary.owl</tt>, <tt>target/lapps-vocabulary.rdf</tt> and
<tt>target/lapps-vocabulary.ttl</tt>.</dd>

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
