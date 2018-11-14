# Generating the LAPPS Grid Vocabulary

This project contains the vocabulary definition (DSL) and templates used to generate the [LAPPS Grid vocabulary](http://vocab.lappsgrid.org) website as well as Java code for the [Discriminator](https://github.com/lapps/org.lappsgrid.discriminator) and [Vocabulary](https://github.com/lapps/org.lappsgrid.vocabulary) modules. The vocabulary and discriminator definitions are manually maintained in `lapps.vocabulary` which has the annotation types for the LAPPS vocabulary and `lapps.discriminators` which has the discriminators.

## Prerequisites

The latest versions of the following programs should be available in the `bin/` directory.

1. **Vocabulary DSL**
  The vocabulary DSL is used to generate the web pages for vocabulary items as well as the Java class for the vocabulary Java API classes. The vocabulary DSL also generates the <tt>vocabulary.config</tt> file that is included by the <tt>discriminators.config</tt> file. The source code for this program is maintained in  https://github.com/lappsgrid-incubator/vocabulary-dsl.<br/>
  [Download](http://downloads.lappsgrid.org/vocab-latest.tgz)

1. **Discriminator DSL**
  The discriminator DSL is used to generated the discriminator web pages for the vocab site as will as the Discriminators Java class for the `org.lappsgrid.discriminator` module. The sources are maintained in https://github.com/lappsgrid-incubator/discriminator-dsl.<br/>
  [Download](http://www.anc.org/downloads/discriminators-latest.tgz)

1. **GitHub Commit**
  The GitHub Commit (ghc) program is a command line program used to commit files to a GitHub repository and create pull requests. Sources are maintained in https://github.com/lappsgrid-incubator/org.lappsgrid.github.commit.<br/>
  [Download](http://www.anc.org/downloads/ghc-latest.tgz)

## Makefile

A Makefile is available to automate most of the tasks involved in generating a new vocabulary web site and related Java classes.  To generate everything simply run:

```bash
$ make all
```

This uses the Vocabulary DSL and the Discriminator DSL and runs them on the two user-generated files `lapps.vocabulary` and `lapps.discriminators`.


Here is an overview of other goals that can be used with the Makefile:

<table>
<tr>
<th>goal</th>
<th>description</th>
</tr>

<tr valign="top">
<td>vocabulary</td>
<td> Generates from <tt>lapps.vocabulary</tt> the <tt>target/vocabulary.config</tt> file, which is used as input by the discriminator dsl</td>
</tr>

<tr valign="top">
<td>html</td>
<td>Generates all html pages for the http:vocab.lappsgrid.org web site. All those pages as well as <tt>css</tt> and <tt>js</tt> directories are written to <tt>target</tt>.</td>
</tr>

<tr valign="top">
<td>java</td>
<td>Generates Java source files for the vocabulary package (https://github.com/lapps/org.lappsgrid.vocabulary) and the discriminators package (https://github.com/lapps/org.lappsgrid.discriminator). The Java classes created (<tt>Annotations.java</tt>, <tt>Features.java</tt> and <tt>Discriminators.java</tt>) are all in the <tt>target</tt> directory.</td>
</tr>

<tr valign="top">
<td>rdf</td>
<td>Generates the vocabulary in all the RDF(-like) formats: rdf, owl, ttl, and json-ld. Files created are
<tt>target/lapps-vocabulary.jsonld</tt>, <tt>target/lapps-vocabulary.owl</tt>, <tt>target/lapps-vocabulary.rdf</tt> and
<tt>target/lapps-vocabulary.ttl</tt>.
</td>
</tr>

<tr valign="top">
<td>all</td>
<td>Does all of the above.</td>
</tr>

<tr valign="top">
<td>clean</td>
<td>Removes all artifacts from previous builds.</td>
</tr>

<tr valign="top">
<td>help</td>
<td>Displays a simple help message.</td>
</tr>

</table>

There is a goal to upload the HTML files to the http://vocab.lappsgrid.org site, but the goal is not portable and must be tailored for each person deploying to the server.  In particular, the person deploying the site must have write permission for the appropriate directories.

There is also a goal to copy the generated Java files to the vocabulary and discriminator projects.  But these are also not portable and must be specially tailored for each person.

## Further Reading

Please see the [LAPPS Grid Wiki](http://wiki.lappsgrid.org/technical/discriminators) for more information.
