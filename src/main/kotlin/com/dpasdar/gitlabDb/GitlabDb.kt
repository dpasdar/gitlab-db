@file:JvmName("GitlabDb")
package com.dpasdar.gitlabDb

import org.gitlab4j.api.GitLabApi
import org.koin.core.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module
import java.io.InputStream

val mainModule = module {
    single {
        GitlabApiWrapper(
            GitLabApi(getProperty("gitlab-db.gitlab_url"), getProperty("gitlab-db.gitlab_token") as String),
                getProperty("gitlab-db.gitlab_repo_project"),
                getProperty("gitlab-db.branch", "master")
        )
    }
}

/**
 * Main library class, providing a wrapper for all provided API
 * Example usage:
 *
 * ```
 * getInstance().getResource("testFile")
 * ```
 * @param gitlabApiWrapper provided automatically by the DIC
 */
class GitlabDbInstance : KoinComponent {
    private val gitlabApiWrapper by inject<GitlabApiWrapper>()

    /**
     * Getting resource from the underlying gitlab repo by name
     * Example usage:
     *
     * ```
     * getInstance().getResource("testFile")
     * ```
     *
     * @param resourceName
     */
    fun getResource(resourceName: String): GitlabResource? = GitlabResource.from(gitlabApiWrapper.getFile(resourceName))

    /**
     * Saving a resource in gitlab repo, if the resource exists,
     * it will be committed with an update message, otherwise it
     * will be created with creation commit message
     * Example usage:
     *
     * ```
     * getInstance().putResource("test data".byteInputStream(), "hello.txt")
     * ```
     *
     * @param resource input stream containing the textual resource
     * @param resourceName the unique name of the resource
     */
    fun putResource(resource : InputStream, resourceName: String) : GitlabResource? =
            GitlabResource.from(gitlabApiWrapper.createOrUpdateFile(resource, resourceName))

    /**
     * If the resource exists, a remove operation will be committed
     * @param resourceName the unique name of the resource
     */
    fun removeResource(resourceName: String) = gitlabApiWrapper.deleteFile(resourceName)

    /**
     * Get a previous version of the file without reverting. If no previous verion, if will return null
     * Example usage:
     *
     * ```
     * //if [latestCommit, Commit1, Commit2]
     * getResourcePreviousVersion("testFile", 2) // ---> will return Commit2
     * ```
     *
     * @param resourceName the unique name of the resource
     * @param numberOfVersionsToSkip how many versions to go back
     */
    @JvmOverloads
    fun getResourcePreviousVersion(resourceName: String, numberOfVersionsToSkip: Long = 1) : GitlabResource? =
        GitlabResource.from(gitlabApiWrapper.getPreviousFileVersion(resourceName, numberOfVersionsToSkip))
}

fun getInstance(): GitlabDbInstance {
    if(GlobalContext.getOrNull() == null) {
        startKoin {
            fileProperties("/application.properties")
            environmentProperties()
            modules(mainModule)
        }
    }
    return GitlabDbInstance()
}