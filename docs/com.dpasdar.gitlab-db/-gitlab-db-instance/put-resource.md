[com.dpasdar.gitlabDb](../index.md) / [GitlabDbInstance](index.md) / [putResource](./put-resource.md)

# putResource

`fun putResource(resource: `[`InputStream`](https://docs.oracle.com/javase/6/docs/api/java/io/InputStream.html)`, resourceName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`GitlabResource`](../-gitlab-resource/index.md)`?`

Saving a resource in gitlab repo, if the resource exists,
it will be committed with an update message, otherwise it
will be created with creation commit message
Example usage:

```
getInstance().putResource("test data".byteInputStream(), "hello.txt")
```

### Parameters

`resource` - input stream containing the textual resource

`resourceName` - the unique name of the resource