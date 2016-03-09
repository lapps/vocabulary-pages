pages:
	./vocab -d lapps.vocab -h element.template -i index.template -o html

package:
	cd html ; tar czf annotations.tgz *.css *.html	

upload:
	anc-put html/annotations.tgz /home/www/anc/LAPPS/vocab
	anc-put target/lapps-vocabulary.rdf /home/www/anc/LAPPS/vocab
	anc-put target/lapps-vocabulary.owl /home/www/anc/LAPPS/vocab
	anc-put target/lapps-vocabulary.jsonld /home/www/anc/LAPPS/vocab
	anc-put target/lapps-vocabulary.ttl /home/www/anc/LAPPS/vocab
	anc-put target/lapps-vocabulary.rdf /home/www/anc/LAPPS/vocab/index.rdf
	anc-put target/lapps-vocabulary.owl /home/www/anc/LAPPS/vocab/index.owl
	anc-put target/lapps-vocabulary.jsonld /home/www/anc/LAPPS/vocab/index.jsonld
	anc-put target/lapps-vocabulary.ttl /home/www/anc/LAPPS/vocab/index.ttl
	
unpack:
	ssh -p 22022 suderman@anc.org 'cd /home/www/anc/LAPPS/vocab ; tar xzf annotations.tgz'

java:
	./vocab -d lapps.vocab -f -j Annotations -o target

site: pages package upload unpack

owl:
	./vocab -d lapps.vocab -o target -r owl
	
rdf:
	./vocab -d lapps.vocab -o target -r rdf
	
ttl:
	./vocab -d lapps.vocab -o target -r ttl
	
jsonld:
	./vocab -d lapps.vocab -o target -r jsonld

rdf-all: owl ttl jsonld

clean:
	rm html/*.html
	rm html/annotations.tgz
	rm -rf target

help:
	@echo
	@echo "GOALS"
	@echo
	@echo "  pages   - Generates all vocabulary pages (default)."
	@echo "  package - Creates a tgz archive of all html pages."
	@echo "  upload  - Uploads the tgz archive to the vocab site."
	@echo "  unpack  - Unpacks the tgz archive on the server."
	@echo "  java    - Generates the Features and Annotations Java files."
	@echo "  clean   - Removes all html pages and the tgz archive."
	@echo

