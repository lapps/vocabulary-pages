VOCABULARY=lapps.vocabulary
DISCRIMINATORS=lapps.discriminators
REMOTE_DIR=/home/www/anc/LAPPS/vocab/beta
REMOTE=anc.org:$(REMOTE_DIR)
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
	chmod a+x bin/vocab
	chmod a+x bin/ddsl
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

ifeq ($(TOKEN),)
commit:
	@echo "Please set the variable TOKEN with your GitHub API token."
	@echo 
	@echo "If you were using the 'make release' goal then you only need"
	@echo "to run 'make commit' after setting TOKEN."
	@echo
else
commit:
	chmod a+x ./bin/ghc
	./bin/ghc -f vocabulary.commit -t $(TOKEN)
	./bin/ghc -f discriminators.commit -t $(TOKEN)
endif

upload:
	if [ -d target/beta ] ; then mv target/beta/ns target/ns ; fi
	cd target ; tar czf annotations.tgz *.html ns js css
	$(SCP) target/annotations.tgz $(REMOTE)
	ssh -p 22022 anc.org "cd "$(REMOTE_DIR)" ; tar xzf annotations.tgz"

upload-rdf:
	$(SCP) target/lapps-vocabulary.rdf $(REMOTE)
	$(SCP) target/lapps-vocabulary.owl $(REMOTE)
	$(SCP) target/lapps-vocabulary.jsonld $(REMOTE)
	$(SCP) target/lapps-vocabulary.ttl $(REMOTE)
	$(SCP) target/lapps-vocabulary.rdf $(REMOTE)/index.rdf
	$(SCP) target/lapps-vocabulary.owl $(REMOTE)/index.owl
	$(SCP) target/lapps-vocabulary.jsonld $(REMOTE)/index.jsonld
	$(SCP) target/lapps-vocabulary.ttl $(REMOTE)/index.ttl

release: all upload upload-rdf commit

clean:
	rm -rf target
