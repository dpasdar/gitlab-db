[com.dpasdar.gitlabDb](../index.md) / [GitlabDbInstance](index.md) / [getResourcePreviousVersion](./get-resource-previous-version.md)

# getResourcePreviousVersion

`@JvmOverloads fun getResourcePreviousVersion(resourceName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, numberOfVersionsToSkip: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = 1): `[`GitlabResource`](../-gitlab-resource/index.md)`?`

Get a previous version of the file without reverting. If no previous verion, if will return null
Example usage:

```
//if [latestCommit, Commit1, Commit2]
getResourcePreviousVersion("testFile", 2) // ---> will return Commit2
```

### Parameters

`resourceName` - the unique name of the resource

`numberOfVersionsToSkip` - how many versions to go back