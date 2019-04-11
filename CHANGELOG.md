# Change Log

All notable changes to the vocabulary and set of discriminators will be documented in this file. The links show all changes between two versions, note that only the changes to `lapps.discriminators` and `lapps.vocabulary` are recorded here.


## Next Version

https://github.com/lapps/vocabulary-pages/compare/1.2.0...develop

* More discriminators for NE tagsets
    - `muc6`
    - `gate`
    - `hepple`
    - `custom` for catch-all


## Version 1.2.0 - 2018-11-14

https://github.com/lapps/vocabulary-pages/compare/1.1.0...1.2.0

* Vocabulary hierarchy is now under the CC-BY license. ([#75](https://github.com/lapps/vocabulary-pages/pull/75))
* Added `tagSet`-like values to `contains` view metadata map for POS, NE, PS trees, and Dependency trees.
    * `posTagSet`, `namedEntityCategorySet`, `categorySet`, `dependencySet`
    * see [#55](https://github.com/lapps/vocabulary-pages/issues/55)
* Added discriminators
    - `tcf` discriminator ([9d93e3](https://github.com/lapps/vocabulary-pages/commit/9d93e3af805960d61fbb4c0fdcfb79a695e0dcf4#diff-4d510d1599c8900360494f2ccf024156))
    - `UD` ([#73](https://github.com/lapps/vocabulary-pages/issues/73))
    - POS tagsets, syntactic category sets, dependency sets, and named entity catgeory sets.


## Version 1.1.0 - 2017-05-18

https://github.com/lapps/vocabulary-pages/compare/1.0.0...1.1.0

- Added versioned discriminators.
- Added `type` meta data property to `Annotation` type.
- Added definition for the `Relation` type.
- Added definition for the `Paragraph` type.
- Changed `NamedEntity`.
- Depcrecated `Data`, `Location`, `Organization` and `Person`.

## Version 1.0.0 - 2017-04-14

https://github.com/lapps/vocabulary-pages/compare/v0.1.0...1.0.0

- First released version
- Renamed `Span` into `Region` and updated definition.
- Made the `id` of an `Annotation` required.
- Added `Relation`, `GenericRelation`, `SemanticRole`, `Paragraph`, `NounChunk`, `VerbChunk` and `Dependency`.
- Added `posTagSet` meta data property to `Token` type.
- Changed type and definition on some properties of `Coreference`.
- Made `Constituent` a sub type of `Relation`.


## Version 0.1.0 - 2015-01-11

- First tagged version.
