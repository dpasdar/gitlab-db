@file:JvmName("GitLabDb")
package com.dpasdar.gitLabDb

import org.gitlab4j.api.GitLabApi
import org.koin.core.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module

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
    getInstance().gitlabApiWrapper.createOrUpdateResource(
        GitlabDbInstance::class.java.classLoader
            .getResourceAsStream("test.txt"),"test2.txt")
}