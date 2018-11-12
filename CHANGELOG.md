#### in discussion 

* adding `dependeOn` to `contains` view metadata map
    * see [#50](https://github.com/lapps/vocabulary-pages/issues/50)

### 1.2.0 

* added `tagSet`-like values to `contains` view metadata map for POS, NE, PS, dep.
    * `posTagSet`, `namedEntityCategorySet`, `categorySet`, `dependencySet`
    * see [#55](https://github.com/lapps/vocabulary-pages/issues/55)
* vocabulary hierarchy is now under CC-BY license. [PR](https://github.com/lapps/vocabulary-pages/pull/75)
* added discriminators
   * for UD [#73](https://github.com/lapps/vocabulary-pages/issues/73)

### 1.1.0 

* re-organized the way [named entity](http://vocab.lappsgrid.org/NamedEntity.html) is structured. 
    * instead of having each type of entity as separate discriminator, now `NamedEntity` is a discriminator that has `type` feature represents entity types (person, organization, location, ...).
