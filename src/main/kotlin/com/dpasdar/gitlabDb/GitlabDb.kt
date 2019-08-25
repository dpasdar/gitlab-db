@file:JvmName("GitLabDb")
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

class GitlabDbInstance : KoinComponent {
    val gitlabApiWrapper by inject<GitlabApiWrapper>()

    fun getResource(resourceName: String): GitlabResource? = GitlabResource.from(gitlabApiWrapper.getFile(resourceName))

    fun putResource(resource : InputStream, resourceName: String) : GitlabResource? =
            GitlabResource.from(gitlabApiWrapper.createOrUpdateFile(resource, resourceName))

    fun removeResource(resourceName: String) = gitlabApiWrapper.deleteFile(resourceName)
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

fun main() {
    getInstance().gitlabApiWrapper.createOrUpdateFile(
        GitlabDbInstance::class.java.classLoader
            .getResourceAsStream("test.txt"),"test2.txt")
}