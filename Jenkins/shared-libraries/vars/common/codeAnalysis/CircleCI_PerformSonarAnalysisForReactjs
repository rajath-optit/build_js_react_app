def CircleCI_PerformSonarAnalysisForReactjs(String projectKey, String organization, String sourcesDir, String sonarCloudCredentialsId) {
    def scannerHome = 'mysonar' // Ensure 'mysonar' matches your SonarQube tool installation in Jenkins

    withCredentials([string(credentialsId: sonarCloudCredentialsId, variable: 'sonarCloudToken')]) {
        // Run the SonarCloud analysis
        withSonarQubeEnv('SonarCloud') {
            sh "/opt/sonar-scanner/sonar-scanner-4.6.2.2472-linux"
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
