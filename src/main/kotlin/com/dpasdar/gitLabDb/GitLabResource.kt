package com.dpasdar.gitLabDb

import org.gitlab4j.api.models.RepositoryFile

class GitLabResource(val name: String) {
    companion object {
        fun from(file: RepositoryFile): GitLabResource {
            return GitLabResource(file.filePath);
        }
    }
    override fun toString(): String = name
}
