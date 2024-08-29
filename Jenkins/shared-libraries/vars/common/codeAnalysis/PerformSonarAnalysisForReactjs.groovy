def performSonarCloudAnalysis(String projectKey, String organization, String sourcesDir, String sonarCloudCredentialsId) {
    def scannerHome = tool 'mysonar' // Ensure 'mysonar' matches your SonarQube tool installation in Jenkins

    withCredentials([string(credentialsId: sonarCloudCredentialsId, variable: 'sonarCloudToken')]) {
        // Run the SonarCloud analysis
        withSonarQubeEnv('SonarCloud') {
            sh """
                ${scannerHome}/bin/sonar-scanner \
                -Dsonar.projectKey=${projectKey} \
                -Dsonar.organization=${organization} \
                -Dsonar.sources=${sourcesDir} \
                -Dsonar.host.url=https://sonarcloud.io \
                -Dsonar.login=${sonarCloudToken} \
                -X
            """
        }
    }
}

return this
