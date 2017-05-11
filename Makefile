VOCABULARY=lapps.vocabulary
DISCRIMINATORS=lapps.discriminators
REMOTE=anc.org:/home/www/anc/LAPPS/vocab
SCP=scp -P 22022

# This is non-portable and requires that the discriminator and vocabulary
# projects have a) been checked out from GitHub, and b) are in the following 
# directories
VOCABULARY_PACKAGE=../org.lappsgrid.vocabulary/src/main/java/org/lappsgrid/vocabulary
DISCRIMINATOR_PACKAGE=../org.lappsgrid.discriminator/src/main/java/org/lappsgrid/discriminator

.PHONY: html

help:
	@echo
	@echo "GOALS"
	@echo 
	@echo "vocabulary - Creates vocab component of the discriminators"
	@echo "html       - Creates all HTML pages for the vocab web site"
	@echo "java       - Generate Java classes for vocabulary and discriminators."
	@echo "rdf        - Generates RDF, OWL, and JSON versions of the vocabulary"
	@echo "all        - Does everything."
	@echo "clean      - Removes all html pages and the tgz archive."
	@echo "help       - Displays this help message." 
	@echo 
	@echo "EXPERIMENTAL"
	@echo
	@echo "copy       - Copies the Java files to their project directories."
	@echo "upload     - Uploads the html files to the vocabulary web site."
	@echo

vocabulary: 
	./bin/vocab --discriminators $(VOCABULARY)

html:
	./bin/vocab --html ./templates/vocab-element.template --index ./templates/vocab-index.template --output target $(VOCABULARY) 
	./bin/ddsl --html ./target/discriminators.html --template ./templates/discriminator-index.template $(DISCRIMINATORS)
	./bin/ddsl --pages target/ --template ./templates/discriminator-page.template $(DISCRIMINATORS)
	cp -rf html/* target

java:
	./bin/vocab  --java Annotations --package org.lappsgrid.vocabulary --output target $(VOCABULARY)
	./bin/vocab --features --output target $(VOCABULARY)
	./bin/ddsl --java $(DISCRIMINATORS)

rdf:
	./bin/vocab --output target --rdf rdf $(VOCABULARY) 
	./bin/vocab --output target --rdf owl $(VOCABULARY) 
	./bin/vocab --output target --rdf ttl $(VOCABULARY) 
	./bin/vocab --output target --rdf jsonld $(VOCABULARY) 

all: vocabulary html java rdf

copy:
	cp target/Discriminators.java $(DISCRIMINATOR_PACKAGE)
	cp target/Annotations.java $(VOCABULARY_PACKAGE)
	cp target/Features.java $(VOCABULARY_PACKAGE)

ifeq ($(TOKEN),)
upload:
	@echo "Please set the variable TOKEN with your GitHub API token."
else
upload:
	if [ -d target/beta ] ; then mv target/beta/ns target/ns ; fi
	cd target ; tar czf annotations.tgz *.html ns js css
	$(SCP) target/annotations.tgz $(REMOTE)
	ssh -p 22022 anc.org "cd "$(REMOTE)" ; tar xzf annotations.tgz"
endif

upload-rdf:
	$(SCP) target/lapps-vocabulary.rdf $(REMOTE)
	$(SCP) target/lapps-vocabulary.owl $(REMOTE)
	$(SCP) target/lapps-vocabulary.jsonld $(REMOTE)
	$(SCP) target/lapps-vocabulary.ttl $(REMOTE)
	$(SCP) target/lapps-vocabulary.rdf $(REMOTE)/index.rdf
	$(SCP) target/lapps-vocabulary.owl $(REMOTE)/index.owl
	$(SCP) target/lapps-vocabulary.jsonld $(REMOTE)/index.jsonld
	$(SCP) target/lapps-vocabulary.ttl $(REMOTE)/index.ttl

clean:
	rm -rf target
