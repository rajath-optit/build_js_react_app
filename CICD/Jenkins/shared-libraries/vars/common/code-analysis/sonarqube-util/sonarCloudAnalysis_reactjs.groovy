def performSonarCloudAnalysisforReactjs(String projectKey, String organization, String sourcesDir, String sonarCloudTokenId) {
  def scannerHome = tool 'mysonar' // Use the name of your SonarQube installation
  println "Using SonarQube scanner from: ${scannerHome}"

  // Run the SonarCloud analysis
  withSonarQubeEnv('SonarCloud') {
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
