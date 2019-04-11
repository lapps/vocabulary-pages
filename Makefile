VERSION=$(shell cat VERSION)

VOCABULARY=lapps.vocabulary
DISCRIMINATORS=lapps.discriminators
REMOTE_DIR=/home/www/anc/LAPPS/vocab
REMOTE=anc.org:$(REMOTE_DIR)
SCP=scp -P 22022
.PHONY: html

help:
	@echo
	@echo "Run 'make release' to generate the vocabulary site from scratch, or"
	@echo "run of the following goals to perform a single task."
	@echo
	@echo "GOALS"
	@echo 
	@echo "vocabulary - Creates vocab component of the discriminators"
	@echo "html       - Creates all HTML pages for the vocab web site"
	@echo "java       - Generate Java classes for vocabulary and discriminators."
	@echo "rdf        - Generates RDF, OWL, and JSON versions of the vocabulary"
	@echo "all        - Generates all files (HTML, JAVA, RDF)"
	@echo "upload     - Uploads HTML files to the server."
	@echo "upload-rdf - Uploads RDF files to the server. (DO NOT USE FOR SNAPSHOT VERSIONS)"
	@echo "commit     - Creates pull requests for the generated Java files."
	@echo "release    - Does everything."
	@echo "clean      - Removes all html pages and the tgz archive."
	@echo "help       - Displays this help message." 
	@echo 

vocabulary: 
	chmod a+x bin/vocab
	chmod a+x bin/ddsl
	./bin/vocab -V $(VERSION) --discriminators $(VOCABULARY)

ifeq ($(TOKEN),)
discriminators: 
	@echo "Please set the variable TOKEN with your GitHub API token."
	@echo 
else
discriminators:
	./bin/ddsl -V $(VERSION) --html ./target/discriminators.html --template ./templates/discriminator-index.template $(DISCRIMINATORS)
	./bin/ddsl -V $(VERSION) --pages target/ --template ./templates/discriminator-page.template $(DISCRIMINATORS)
	cp -rf html/* target
	./bin/ddsl -V $(VERSION) --java $(DISCRIMINATORS)	
	./bin/ghc -f discriminators.commit -t $(TOKEN)
endif
	
html:
	./bin/vocab -V $(VERSION) --html ./templates/vocab-element.template --index ./templates/vocab-index.template --output target $(VOCABULARY) 
	./bin/ddsl -V $(VERSION) --html ./target/discriminators.html --template ./templates/discriminator-index.template $(DISCRIMINATORS)
	./bin/ddsl -V $(VERSION) --pages target/ --template ./templates/discriminator-page.template $(DISCRIMINATORS)
	cp -rf html/* target

java:
	./bin/vocab -V $(VERSION) --java Annotations --package org.lappsgrid.vocabulary --output target $(VOCABULARY)
	./bin/vocab -V $(VERSION) --features --output target $(VOCABULARY)
	./bin/ddsl -V $(VERSION) --java $(DISCRIMINATORS)	

rdf:
	./bin/vocab -V $(VERSION) --output target --rdf rdf $(VOCABULARY) 
	./bin/vocab -V $(VERSION) --output target --rdf owl $(VOCABULARY) 
	./bin/vocab -V $(VERSION) --output target --rdf ttl $(VOCABULARY) 
	./bin/vocab -V $(VERSION) --output target --rdf jsonld $(VOCABULARY) 
	./bin/vocab -V $(VERSION) --output target --xsd $(VOCABULARY)
	
xsd:
	./bin/vocab -V $(VERSION) --output target --xsd $(VOCABULARY)
	
all: clean vocabulary html java rdf

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
	mkdir target/$(VERSION)
	cp target/lapps-vocabulary.* target/$(VERSION)
	cd target ; cp -R *.html *.xsd js ns css $(VERSION) && tar czf annotations.tgz $(VERSION)
	$(SCP) target/annotations.tgz $(REMOTE)
	ssh -p 22022 anc.org "sudo /usr/local/bin/untar-vocab.sh"

deploy:
	$(SCP) target/annotations.tgz $(REMOTE)
	ssh -p 22022 anc.org "sudo /usr/local/bin/untar-vocab.sh"

upload-rdf:
	$(SCP) target/lapps-vocabulary.rdf $(REMOTE)
	$(SCP) target/lapps-vocabulary.owl $(REMOTE)
	$(SCP) target/lapps-vocabulary.jsonld $(REMOTE)
	$(SCP) target/lapps-vocabulary.ttl $(REMOTE)
	$(SCP) target/lapps-vocabulary.rdf $(REMOTE)/index.rdf
	$(SCP) target/lapps-vocabulary.owl $(REMOTE)/index.owl
	$(SCP) target/lapps-vocabulary.jsonld $(REMOTE)/index.jsonld
	$(SCP) target/lapps-vocabulary.ttl $(REMOTE)/index.ttl

# The upload-rdf goal needs to be fixed before this can be safely used. Currently
# upload-rdf will stomp all over the current release files when a SNAPSHOT is
# deployed.
#release: all upload upload-rdf commit

clean:
	rm -rf target
