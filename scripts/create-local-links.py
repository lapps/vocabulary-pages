"""create-local-links.py

When you create the HTML pages with "make all" from the toplevel directory in
this repository, it creates a local directory named "target" with in it all HTML
pages intended to be put on the vocab.lappsgrid.org website. These pages can be
viewed locally. However, one of the pages, discriminators.html, has links to
pages at vocab.lappsgrid.org which makes it hard to inspect the discriminators
locally from a browser.

This script replaces all absolute links to http://vocab.lappsgrid.org/ with
relative links to local pages.

USAGE:

$ python create-local-links.py SOURCE_DIR TARGET_DIR

Typically, when you call this script from the directory it is in, SOURCE_DIR
will be equal to "../target". Note that TARGET_DIR may not exist.

"""

import os, sys, re, shutil


DISCRIMINATORS = 'discriminators.html'

EXPRESSION1 = r"<a href='http://vocab.lappsgrid.org/(\S+?)(#\S+?)'>"
EXPRESSION2 = r"<a href='http://vocab.lappsgrid.org/(\S+?)'>"

REPLACEMENT1 = r"<a href='\1.html\2'>"
REPLACEMENT2 = r"<a href='\1.html'>"


def process_discriminators(source, target):
    infile = os.path.join(source, DISCRIMINATORS)
    outfile = os.path.join(target, DISCRIMINATORS)
    with open(infile) as fh1, open(outfile, 'w') as fh2:
        for line in fh1:
            line = re.sub(EXPRESSION1, REPLACEMENT1, line)
            line = re.sub(EXPRESSION2, REPLACEMENT2, line)
            fh2.write(line)


if __name__ == '__main__':

    source = sys.argv[1]
    target = sys.argv[2]
    
    if os.path.exists(target):
        exit("WARNING: %s already exists, exiting..." % target)
        
    shutil.copytree(source, target)
    process_discriminators(source, target)
