def performSonarCloudAnalysisforReactjs(String projectKey, String organization, String sourcesDir, String sonarCloudTokenId) {
    def scannerHome = tool 'mysonar' // Use the name of your SonarQube installation
    println "Using SonarQube scanner from: ${scannerHome}"
    def sonarCloudToken = credentials(sonarCloudTokenId).replaceAll("[\n\r]", "") // Remove any newline characters

    // Debug output
    println "Project Key: ${projectKey}"
    println "Organization: ${organization}"
    println "Sources Directory: ${sourcesDir}"
    println "SonarCloud Token: ${sonarCloudToken}"

    // Run the SonarCloud analysis
    withSonarQubeEnv('SonarCloud') {
        def scannerCmd = "${scannerHome}/bin/sonar-scanner " +
                         "-Dsonar.projectKey=${projectKey} " +
                         "-Dsonar.organization=${organization} " +
                         "-Dsonar.sources=${sourcesDir} " +
                         "-Dsonar.host.url=https://sonarcloud.io " +
                         "-Dsonar.login=${sonarCloudToken} " +
                         "-X"

        sh(script: scannerCmd, returnStatus: true) // Execute the scanner command and capture return status
    }
}

return this
