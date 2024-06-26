def getPipelineParameters() {
    return [
        string(name: 'DSL_GIT_URL', description: 'Git repository URL for shared library'),
        string(name: 'DSL_BRANCH_NAME', description: 'Branch name of the shared library'),
        credentials(name: 'GIT_DSL_CREDENTIAL', description: 'Jenkins credential ID for the shared library Git repository', required: true),
        string(name: 'GIT_SOURCE_CODE_URL', description: 'Git repository URL for the source code'),
        string(name: 'SOURCE_CODE_BRANCH_NAME', description: 'Branch name of the source code repository'),
        credentials(name: 'GIT_SOURCE_CODE_CREDENTIAL', description: 'Jenkins credential ID for the source code Git repository', required: true),
        string(name: 'DOCKER_REPO', description: 'Docker repository name'),
        string(name: 'DOCKER_IMAGE_NAME', description: 'Name of the Docker image'),
        string(name: 'DOCKER_IMAGE_TAG', description: 'Tag for the Docker image'),
        credentials(name: 'DOCKER_CREDENTIAL', description: 'Jenkins credential ID for DockerHub', required: true),
        string(name: 'SONAR_PROJECT_KEY', description: 'SonarQube project key'),
        string(name: 'SONAR_ORGANIZATION', description: 'SonarQube organization key'),
        string(name: 'SONAR_SOURCES_DIR', description: 'Directory for SonarQube source code analysis'),
        credentials(name: 'SONAR_TOKEN', description: 'SonarQube authentication token', required: true),
        string(name: 'SONAR_SCANNER_HOME', description: 'SonarQube scanner tool name'),
        string(name: 'SONAR_ENV_NAME', description: 'SonarQube environment name'),
        string(name: 'SONAR_HOST_URL', description: 'URL of the SonarQube server'),
        string(name: 'SONAR_LANGUAGE', description: 'Programming language of the project'),
       
    ]
}

return this
