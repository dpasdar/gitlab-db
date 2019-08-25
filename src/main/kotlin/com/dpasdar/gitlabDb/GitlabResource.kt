package com.dpasdar.gitlabDb

import org.gitlab4j.api.models.RepositoryFile

data class GitlabResource(val name: String? = "", val content: String? = "") {
    companion object {
        fun from(file: RepositoryFile?): GitlabResource {
            return GitlabResource(file?.filePath, file?.decodedContentAsString);
        }
    }
}
