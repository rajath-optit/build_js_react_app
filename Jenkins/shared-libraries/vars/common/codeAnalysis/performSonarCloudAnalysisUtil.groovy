
def performSonarCloudAnalysis(String projectKey, String organization, String sourcesDir, String binariesDir, String sonarCloudToken, String scannerHomeToolName, String sonarEnvName) {

    def scannerHome = tool scannerHomeToolName // Use the name of your SonarQube installation from parameters
    println "Using SonarQube scanner from: ${scannerHome}"

    // Run the SonarCloud analysis
    script {
        def scannerArgs = [
            "-Dsonar.projectKey=${projectKey}",
            "-Dsonar.organization=${organization}",
            "-Dsonar.sources=${sourcesDir}",
            "-Dsonar.java.binaries=${binariesDir}",
            "-Dsonar.host.url=https://sonarcloud.io",
            "-Dsonar.login=${sonarCloudToken}"
        ]

        if (sonarEnvName) {
            scannerArgs << "-Dsonar.exclusions=${sonarEnvName}"
        }

        withSonarQubeEnv(sonarEnvName ?: 'SonarCloud') {
            sh """
                ${scannerHome}/bin/sonar-scanner ${scannerArgs.join(' ')}
            """
        }
    }
}

return this
