pages:
	./vocab -d lapps.vocab -h element.template -i index.template -o html

package:
	cd html ; tar czf annotations.tgz *.css *.html	

upload:
	anc-put html/annotations.tgz /home/www/anc/LAPPS/vocab

unpack:
	ssh -p 22022 suderman@anc.org 'cd /home/www/anc/LAPPS/vocab ; tar xzf annotations.tgz'

java:
	./vocab -d lapps.vocab -f -j Annotations -o target

site: pages package upload unpack

rdf:
	./vocab -d lapps.vocab -o target -r rdf
	./vocab -d lapps.vocab -o target -r ttl
	./vocab -d lapps.vocab -o target -r jsonld

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

