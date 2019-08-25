package com.dpasdar.gitlabDb

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.gitlab4j.api.CommitsApi
import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.RepositoryApi
import org.gitlab4j.api.RepositoryFileApi
import org.gitlab4j.api.models.Branch
import org.gitlab4j.api.models.Commit
import org.gitlab4j.api.models.RepositoryFile
import org.gitlab4j.api.models.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*

class GitlabApiWrapperTest {
    val repositoryFileApiMock = mockk<RepositoryFileApi>(relaxed = true)
    val commitsApiMock = mockk<CommitsApi>(relaxed = true)
    val repositoryApiMock = mockk<RepositoryApi>()
    val apiMock = mockk<GitLabApi>()
    var sut : GitlabApiWrapper? = null

    @BeforeEach
    fun setUp() {
        every { apiMock.repositoryApi } returns repositoryApiMock
        every { apiMock.repositoryFileApi } returns repositoryFileApiMock
        every { apiMock.commitsApi } returns commitsApiMock
        every { repositoryApiMock.getOptionalBranch("project", "branch") } returns Optional.of(Branch())
        sut = GitlabApiWrapper(apiMock, "project", "branch")
    }

    fun mockGetFileFromGitlabWithContent(fileContent : String, ref: String = "branch") {
        every { repositoryFileApiMock.getOptionalFile("project", "test1", ref) } returns
                Optional.of(RepositoryFile().apply { content = fileContent })
    }

    @Test
    fun `create or update file should check for file existence and should update if the file exists`() {
        mockGetFileFromGitlabWithContent("some data")
        val mockInputStream = "test data".byteInputStream()

        sut?.createOrUpdateFile(mockInputStream, "test1")
        verify(exactly = 0) {
            repositoryFileApiMock.createFile("project", any(), "branch", any())
        }

        verify(exactly = 1) {
            repositoryFileApiMock.updateFile("project", any(), "branch", any())
        }
    }

    @Test
    fun `update file should not send a commit if the content of the file is the same`() {
        mockGetFileFromGitlabWithContent("same data")
        val mockInputStream = "same data".byteInputStream()

        sut?.createOrUpdateFile(mockInputStream, "test1")
        verify(exactly = 0) {
            repositoryFileApiMock.createFile("project", any(), "branch", any())
            repositoryFileApiMock.updateFile("project", any(), "branch", any())
        }
    }

    @Test
    fun `getting history of the file should return the correct history object`() {
        mockGetFileFromGitlabWithContent("some data", "oneVersionBefore")
        every { commitsApiMock.getCommits("project", "branch", "test1") } returns
                listOf(
                    Commit().apply{
                        id = "latestCommit"
                    },
                    Commit().apply{
                        id = "oneVersionBefore"
                    },
                    Commit().apply{
                        id = "twoVersionsBefore"
                    })
        sut?.getPreviousFileVersion("test1", 1)
        verify(exactly = 1) {
            repositoryFileApiMock.getOptionalFile("project", "test1", "oneVersionBefore")
        }
    }
}