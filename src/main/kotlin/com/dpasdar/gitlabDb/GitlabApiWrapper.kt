package com.dpasdar.gitlabDb

import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.models.RepositoryFile
import java.io.InputStream
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*
import java.util.stream.Collectors

class GitlabApiWrapper(val api: GitLabApi, val projectName: String, val branchName: String) {
    init {
        createBranchIfNotExists(branchName)
    }

    fun getResources() : List<GitlabResource> {
        return api.repositoryApi.getTreeStream(projectName)
            .map { GitlabResource(it.name) }
            .collect(Collectors.toList())
    }

    private fun createResource(input: InputStream, fileName: String): RepositoryFile {
        val output = createRepositoryFileWithContent(fileName, input)
        api.repositoryFileApi.createFile(projectName, output, branchName, "file added");
        return output
    }

    private fun createBranchIfNotExists(branchName: String) {
        val optionalBranch = api.repositoryApi.getOptionalBranch(projectName, branchName)
        if(!optionalBranch.isPresent) {
            api.repositoryApi.createBranch(projectName, branchName, "master")
        }
    }

    private fun createRepositoryFileWithContent(
        fileName: String,
        input: InputStream
    ): RepositoryFile {
        val output = RepositoryFile();
        output.fileName = fileName;
        output.filePath = fileName;
        output.content = input.readBytes().toString(Charset.defaultCharset())//input.reader().use { it.readText() }
        return output
    }

    fun createOrUpdateFile(input: InputStream, fileName: String)
            : RepositoryFile {
        val optionalFile = api.repositoryFileApi.getOptionalFile(projectName, fileName, branchName)
        if(optionalFile.isPresent) {
            val output = createRepositoryFileWithContent(fileName, input)
            if(output.content.md5() != optionalFile.get().decodedContentAsString.md5())
                return api.repositoryFileApi.updateFile(projectName, output, branchName, "file updated")
            else
                return optionalFile.get()
        } else {
            return createResource(input, fileName)
        }
    }

    fun getFile(fileName: String)
            : RepositoryFile? {
        return api.repositoryFileApi
                .getOptionalFile(projectName, fileName, branchName)
                .toNullable()
    }

    fun deleteFile(fileName: String) {
        api.repositoryFileApi.deleteFile(projectName, fileName, branchName, "file deleted")
    }

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }
    fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null);
}