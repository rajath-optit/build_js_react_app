def performSonarCloudAnalysisforReactjs(String projectKey, String organization, String sourcesDir, String sonarCloudTokenId) {
def scannerHome = tool name: 'SonarQubeScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    withSonarQubeEnv('SonarQube') {
        sh """
            ${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectKey=my-react-project \
            -Dsonar.sources=src \
            -Dsonar.host.url=${env.SONAR_HOST_URL} \
            -Dsonar.login=${env.SONAR_LOGIN}
        """
    }
}
return this
