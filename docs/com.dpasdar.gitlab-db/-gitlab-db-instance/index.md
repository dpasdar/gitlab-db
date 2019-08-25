[com.dpasdar.gitlabDb](../index.md) / [GitlabDbInstance](./index.md)

# GitlabDbInstance

`class GitlabDbInstance : KoinComponent`

Main library class, providing a wrapper for all provided API
Example usage:

```
getInstance().getResource("testFile")
```

### Parameters

`gitlabApiWrapper` - provided automatically by the DIC

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `GitlabDbInstance()`<br>Main library class, providing a wrapper for all provided API Example usage: |

### Functions

| Name | Summary |
|---|---|
| [getResource](get-resource.md) | `fun getResource(resourceName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`GitlabResource`](../-gitlab-resource/index.md)`?`<br>Getting resource from the underlying gitlab repo by name Example usage: |
| [getResourcePreviousVersion](get-resource-previous-version.md) | `fun getResourcePreviousVersion(resourceName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, numberOfVersionsToSkip: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = 1): `[`GitlabResource`](../-gitlab-resource/index.md)`?`<br>Get a previous version of the file without reverting. If no previous verion, if will return null Example usage: |
| [putResource](put-resource.md) | `fun putResource(resource: `[`InputStream`](https://docs.oracle.com/javase/6/docs/api/java/io/InputStream.html)`, resourceName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`GitlabResource`](../-gitlab-resource/index.md)`?`<br>Saving a resource in gitlab repo, if the resource exists, it will be committed with an update message, otherwise it will be created with creation commit message Example usage: |
| [removeResource](remove-resource.md) | `fun removeResource(resourceName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>If the resource exists, a remove operation will be committed |
