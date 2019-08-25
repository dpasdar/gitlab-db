[com.dpasdar.gitlabDb](../index.md) / [GitlabDbInstance](index.md) / [getResource](./get-resource.md)

# getResource

`fun getResource(resourceName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`GitlabResource`](../-gitlab-resource/index.md)`?`

Getting resource from the underlying gitlab repo by name
Example usage:

```
getInstance().getResource("testFile")
```

### Parameters

`resourceName` - 