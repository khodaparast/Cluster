# Clustering

This project processes Stack Overflow post data to compute relationships between tags. It is written in Java and generates dissimilarity matrices between tags.

## General Structure

The repository is a small Java project consisting of two Java source files and several CSV/XLSX data files.

```
Cluster/
├── Util.java
├── XMLParser.java
├── *.csv                  (precomputed cluster and dissimilarity matrices)
├── samplePosts2.xml        (small example of the Posts.xml dataset)
└── google-collections.jar  (dependency for HashMultimap)
```

## Key Components

1. **`XMLParser`** – entry point
   - Reads an XML dataset of Stack Overflow posts, e.g. `Posts.xml`.
   - Uses StAX (`XMLStreamReader`) to iterate through `<row>` elements.
   - For each question post (`PostTypeId="1"`), extracts the `Tags` attribute and forwards it to `Util.questionOFTags`.
   - After parsing, calls `Util.getDissimilarityMap` and `Util.makeDissimMatrix` to build a tag dissimilarity matrix.

2. **`Util`** – tag processing and matrix generation
   - Holds hard-coded comma-separated lists of tags for different languages (only the .NET list is active by default).
   - `questionOFTags` cleans a tag string and maps each tag to the question ID using a `HashMultimap`.
   - `getDissimilarityMap` calculates a Jaccard-like distance between every pair of tags using the collected question IDs.
   - `makeDissimMatrix` writes the resulting matrix to CSV. The output path is currently hardcoded.

3. **Data files**
   - Several CSV files contain precomputed cluster assignments or distance matrices.
   - `samplePosts2.xml` is a short example of the Stack Overflow dataset format.

## Important Points

- The code expects the large `Posts.xml` dataset at a specific path. Adjust this path or use the provided `samplePosts2.xml` for testing.
- Google Collections (`google-collections.jar`) must be on the classpath when compiling and running.
- Output file paths are also hardcoded and may need to be adjusted.
- Dissimilarity calculation loops over every pair of tags, which can be slow for large tag sets.

## Suggestions for Further Learning

- Automate configuration through command-line arguments or configuration files instead of hardcoded paths.
- Explore clustering algorithms using the generated dissimilarity matrices.
- Consider optimizing performance for large datasets.
- Introduce build tools (e.g., Maven or Gradle) and add unit tests for maintainability.

