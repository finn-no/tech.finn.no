tech.finn.no
============
GH-pages URL: http://finn-no.github.io/tech.finn.no/
## Work flow

All changes are made in source and generated site lives in master branch.
Here is how you make changes:
* git branch -D master
* git checkout -b master
* git filter-branch --subdirectory-filter _site/ -f
* git checkout source
* git push --all origin
