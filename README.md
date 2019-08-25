# gitlab-db [![Build Status](https://dev.azure.com/dpasdar/dpasdar/_apis/build/status/dpasdar.gitlab-db?branchName=master)](https://dev.azure.com/dpasdar/dpasdar/_build/latest?definitionId=1&branchName=master)
A simple library written in Kotlin to store and retrieve text files from GitLab using the GitLab API, effectively using it as some sort of database or content repository.

## Local Build
Using gradle, issue the following command to build a pom
```
gradle clean publish
```

## Using in Java
After including the generated maven artifact, make sure the following parameters are provided (either in application.properties or as ENV variables):
```
gitlab-db.gitlab_url="https://gitlab.com"
gitlab-db.gitlab_repo_project="gitlab project id"
gitlab-db.gitlab_token="private token"
gitlab-db.branch="master"
```

Afterwards, you can get an instance of the API using 
```
GitlabDb.getInstance().*
```

## Documentation
[Auto generated Dokka documentation](https://dpasdar.github.io/gitlab-db/com.dpasdar.gitlab-db/-gitlab-db-instance/)
