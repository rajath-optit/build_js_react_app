def sonarCloudAnalysis(String projectKey, String organization, String sourcesDir, String binariesDir, String sonarCloudToken) {
 
  def scannerHome = tool 'mysonar' // Use the name of your SonarQube installation
  println "Using SonarQube scanner from: ${scannerHome}"

  // Run the SonarCloud analysis
  withSonarQubeEnv('SonarCloud') {
    sh """
         /var/lib/jenkins/tools/hudson.plugins.sonar.SonarRunnerInstallation/mysonar/bin/sonar-scanner \
        -Dsonar.projectKey=${projectKey} \
        -Dsonar.organization=${organization} \
        -Dsonar.sources=${sourcesDir} \
        -Dsonar.java.binaries=${binariesDir} \
        -Dsonar.host.url=https://sonarcloud.io \
        -Dsonar.login=${sonarCloudToken}\
        -X
    """
  }
}

return this
