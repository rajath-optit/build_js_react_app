def defineParameters() {
    return [
        string(name: 'GIT_SOURCE_CODE_URL', description: 'Git repository URL for source code'),
        string(name: 'SOURCE_CODE_BRANCH_NAME', description: 'Source Code Branch to build'),
        credentials(name: 'GIT_SOURCE_CODE_CREDENTIAL', description: 'Jenkins credential for source code Git repo', required: true),
        string(name: 'DOCKER_REPO', description: 'Docker repository'),
        string(name: 'DOCKER_IMAGE_NAME', description: 'Docker image name'),
        string(name: 'DOCKER_IMAGE_TAG', description: 'Docker image tag'),
        credentials(name: 'DOCKER_CREDENTIAL', description: 'Jenkins credential for DockerHub', required: true),
        string(name: 'SONAR_PROJECT_KEY', description: 'SonarQube project key'),
        string(name: 'SONAR_ORGANIZATION', description: 'SonarQube organization key'),
        string(name: 'SONAR_SOURCES_DIR', description: 'Source code directory for SonarQube analysis'),
        credentials(name: 'SONAR_TOKEN', description: 'SonarQube authentication token', required: true),
        choice(name: 'BUILD_TOOL', choices: ['npm', 'maven'], description: 'Build tool to use')
    ]
}
return this
