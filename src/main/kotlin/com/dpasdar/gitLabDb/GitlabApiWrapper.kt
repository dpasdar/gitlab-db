package com.dpasdar.gitLabDb

import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.models.RepositoryFile
import java.io.InputStream
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.stream.Collectors

class GitlabApiWrapper(val api: GitLabApi, val projectName: String, val branchName: String) {
    init {
        checkAndCreateBranch(branchName)
    }
    fun getResources() : List<GitLabResource> {
        return api.repositoryApi.getTreeStream(projectName)
            .map { GitLabResource(it.name) }
            .collect(Collectors.toList())
    }

    fun createResource(input: InputStream, resourceName: String): GitLabResource {
        val output = createRepositoryFileWithContent(resourceName, input)
        api.repositoryFileApi.createFile(projectName, output, branchName, "file added");
        return GitLabResource.from(output)
    }

    private fun checkAndCreateBranch(branchName: String) {
        val optionalBranch = api.repositoryApi.getOptionalBranch(projectName, branchName)
        if(!optionalBranch.isPresent) {
            api.repositoryApi.createBranch(projectName, branchName, "master")
        }
    }

    private fun createRepositoryFileWithContent(
        resourceName: String,
        input: InputStream
    ): RepositoryFile {
        val output = RepositoryFile();
        output.fileName = resourceName;
        output.filePath = "/$resourceName";
        output.content = input.readBytes().toString(Charset.defaultCharset())//input.reader().use { it.readText() }
        return output
    }

    fun createOrUpdateResource(input: InputStream, resourceName: String)
            : GitLabResource {
        val optionalFile = api.repositoryFileApi.getOptionalFile(projectName, resourceName, branchName)
        if(optionalFile.isPresent) {
            val output = createRepositoryFileWithContent(resourceName, input)
            if(output.content.md5() != optionalFile.get().decodedContentAsString.md5())
                return GitLabResource.from(api.repositoryFileApi.updateFile(projectName, output, branchName, "file updated"))
            else
                return GitLabResource.from(optionalFile.get())
        } else {
            return createResource(input, resourceName)
        }
    }

    fun deleteResource(resourceName: String) {
        api.repositoryFileApi.deleteFile(projectName, resourceName, branchName, "file deleted")
    }

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
}