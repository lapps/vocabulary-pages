pages:
	vocab -d lapps.vocab -h element.template -i index.template -o html

package:
	cd html ; tar czf annotations.tgz *.html	

upload:
	anc-put html/annotations.tgz /home/www/anc/LAPPS/vocab

clean:
	rm html/*.html
	rm html/annotations.tgz

help:
	@echo "GOALS"
	@echo
	@echo "pages   - Generates all vocabulary pages."
	@echo "package - Creates a tgz archive of all html pages."
	@echo "upload  - Uploads the tgz archive to the vocab site."
	@echo "clean   - Removes all html pages and the tgz archive."
	@echo

 