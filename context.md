# Notes on the LAPPS Context

As of June 2020 we only had one context, living at http://vocab.lappsgrid.org/context-1.0.0.jsonld. This version appears to have been used for all vocabularies up to version 1.2.0 and it did not appear to be under version control.

As an initial fix we took that context and put it in the top-level of this repository, under the name used before, and created a new version to fit with vocabulary version 1.2.0, which is what was published at the time. The new version was created manually, but future versions should be created automatically from the vocabulary, as suggested several times in https://github.com/lapps/vocabulary-pages/issues/23.

Some notes. 

- The new context only includes elements that are structural elements of LIF (views, text, metadata, etcetera) or that are specifically mentioned in the vocabulary. With that, we can get a better handle on automatically creating the context file from the schema and the vocabulary, including the discriminators. This means does mean that non-specified features should be fully expanded or use the annotation type, for example `NamedEntity#location` if you have a landmark named entity and you want to specify the location. The above will expand to a full URL, but that URL will not have a definition of the feature in it. The same problem plays out for the `id` property which is used as a feature on the annotation type as well as a property on a view and the `type` property which is expressed both as a metadata feature on `Annotation` and a regular feature on `NamedEntity`.
- When values of features are URIs then we use `@id` and `@type` in the context. When the value is a string or a URI then `@id` and `@type` are not used.
- Some features are ambiguous. For example, we have `type` in both the view metadata and named entities, we would like one to expand to http://vocab.lappsgrid.org/metadata/type and the other http://vocab.lappsgrid.org/namedEntity#type. With only one context we cannot do that so I chose to have type expand to the first and if you need the second expansion then you need to use `ne:type` for the second. Services would need to create the right features.
- Not included are most of the discriminators. Too much to do manually. So for now these need to be entered into the LIF object in their full expanded form. Lines in the old context that smelled like they were aimed at discriminators were not removed (these all have `type:` as part of the line).
- Any unknown terms `term` will be expanded to `http://vocab.lappsgrid.org/term` which may or may not be what we want.

The context was tested with the JSON-LD playground at https://json-ld.org/playground/.



## Versioning Issues

The new version was named `context-1.2.0.jsonld`, this is awaiting final decisions and/or clarity on how we will deal with the version numbers and how they impact the output of services (which now all point to `contenx-1.0.0.jsonld`).



## JSON-LD Notes

A note on JSONLD and the context since to this day I have not seen this spelled out in any introduction. Say we use the following in the context.

```json
{
 "type": {
  "@id": "meta:type",
  "@type": "@id"
 }
}
```

Then the first object below will be expanded into the second (assuming tokenizer expands to the URL). 

```json
{
  "type": "tokenizer:stanford"
}

{
  "http://vocab.lappsgrid.org/metadata/type": {
    "@id": "http://vocab.lappsgrid.org/types/tokenizer/stanford"
  }
}
```

However, if we have the following the context

```json
{
  "type": {
    "@id": "meta:type"
  }
}
```

Then the expansion for that same object is not quite what we want.

```json
{
  "http://vocab.lappsgrid.org/metadata/type": "tokenizer:stanford"
}
```

Related to the above is what happens when you have a features whose value is supposed to be a list, for example with the meta property `type` on `Annotation`. The first object below is expanded to the second.

```json
{
  "type": ["tokenizer:stanford", "tokenizer:opennlp"]
}

{
  "http://vocab.lappsgrid.org/Annotation#type": [
    {
      "@id": "tokenizer:stanford"
    },
    {
      "@id": "tokenizer:opennlp"
    }
  ]
}
```

And that happens even though the context does not say anything about lists.

