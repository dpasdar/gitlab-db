[com.dpasdar.gitlabDb](../index.md) / [GitlabApiWrapper](./index.md)

# GitlabApiWrapper

`class GitlabApiWrapper`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `GitlabApiWrapper(api: GitLabApi, projectName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, branchName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [api](api.md) | `val api: GitLabApi` |
| [branchName](branch-name.md) | `val branchName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [projectName](project-name.md) | `val projectName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [createOrUpdateFile](create-or-update-file.md) | `fun createOrUpdateFile(input: `[`InputStream`](https://docs.oracle.com/javase/6/docs/api/java/io/InputStream.html)`, fileName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): RepositoryFile` |
| [deleteFile](delete-file.md) | `fun deleteFile(fileName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getFile](get-file.md) | `fun getFile(fileName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, ref: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = branchName): RepositoryFile?` |
| [getPreviousFileVersion](get-previous-file-version.md) | `fun getPreviousFileVersion(fileName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, numberOfVersionsToSkip: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)` = 1): RepositoryFile?` |
| [getResources](get-resources.md) | `fun getResources(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`GitlabResource`](../-gitlab-resource/index.md)`>` |
| [md5](md5.md) | `fun `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`.md5(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [toNullable](to-nullable.md) | `fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> `[`Optional`](https://docs.oracle.com/javase/6/docs/api/java/util/Optional.html)`<`[`T`](to-nullable.md#T)`>.toNullable(): `[`T`](to-nullable.md#T)`?` |
