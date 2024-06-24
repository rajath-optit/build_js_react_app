import com.cloudbees.plugins.credentials.CredentialsProvider
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials

def performSonarCloudAnalysisforReactjs(String projectKey, String organization, String sourcesDir, String sonarCloudTokenId) {
    def scannerHome = tool 'mysonar' // Use the name of your SonarQube installation
    println "Using SonarQube scanner from: ${scannerHome}"
    
    // Retrieve SonarCloud token from Jenkins credentials
    def credentials = CredentialsProvider.findCredentialById(sonarCloudTokenId, StandardUsernamePasswordCredentials.class, Jenkins.instance, [])
    if (credentials == null) {
        error "Could not find credentials with ID: ${sonarCloudTokenId}"
        return
    }
    def sonarCloudToken = credentials.password

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
