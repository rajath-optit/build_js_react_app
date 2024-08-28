def sonarqubeWithPropertiesFile(String toolName, String sonarPropertiesFilePath) {
    def scannerHome = tool toolName // Set the scanner home using the tool name
    println "Using SonarQube scanner from: ${scannerHome}"
    println "Loading SonarQube properties from: ${sonarPropertiesFilePath}"
    
    // Read SonarQube properties from sonar-project.properties file
    def sonarProps = [:]
    def lines = readFile(sonarPropertiesFilePath).split('\n')
    
    lines.each { line ->
        def parts = line.split('=')
        if (parts.size() == 2) {
            sonarProps[parts[0].trim()] = parts[1].trim()
        } else {
            println "Warning: Skipping malformed line: ${line}"
        }
    }

    // Retrieve SonarQube server URL and authentication token from properties
    def sonarUrl = sonarProps['sonar.host.url'] ?: 'https://sonarcloud.io' // Use default URL if not set
    def sonarToken = sonarProps['sonar.login']

    // Run Sonar analysis depending on the build tool
    def buildTool = sonarProps['build.tool'] ?: 'maven' // Default to Maven if build.tool not specified
    
    if (buildTool == 'maven') {
        // Run Maven Sonar analysis
        sh """
            ${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectKey=${sonarProps['sonar.projectKey']} \
            -Dsonar.organization=${sonarProps['sonar.organization']} \
            -Dsonar.sources=${sonarProps['sonar.sources']} \
            -Dsonar.java.binaries=${sonarProps['sonar.java.binaries']} \
            -Dsonar.host.url=${sonarUrl} \
            -Dsonar.login=${sonarToken} \
            -X
        """
    } else if (buildTool == 'gradle') {
        // Run Gradle Sonar analysis
        def buildGradlePath = "build.gradle"
        println "Using build.gradle from: ${buildGradlePath}"
        
        // Check if sonarqube block exists in build.gradle
        def buildGradleContent = readFile(buildGradlePath)
        def sonarqubeBlockExists = buildGradleContent.contains("sonarqube")

        if (sonarqubeBlockExists) {
            // Update existing SonarQube configuration in build.gradle
            def updatedBuildGradleContent = buildGradleContent.replaceAll(/sonar\.host\.url=.*/, "sonar.host.url=${sonarUrl}")
                                                             .replaceAll(/sonar\.login=.*/, "sonar.login=${sonarToken}")
            writeFile file: buildGradlePath, text: updatedBuildGradleContent
        } else {
            // Create new SonarQube configuration in build.gradle
            def newSonarQubeBlock = """
                sonarqube {
                    properties {
                        property "sonar.host.url", "${sonarUrl}"
                        property "sonar.login", "${sonarToken}"
                    }
                }
            """
            writeFile file: buildGradlePath, text: buildGradleContent + "\n\n" + newSonarQubeBlock
        }

        // Run the Gradle Sonar analysis
        sh """
            ${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectKey=${sonarProps['sonar.projectKey']} \
            -Dsonar.organization=${sonarProps['sonar.organization']} \
            -Dsonar.sources=${sonarProps['sonar.sources']} \
            -Dsonar.java.binaries=${sonarProps['sonar.java.binaries']} \
            -Dsonar.host.url=${sonarUrl} \
            -Dsonar.login=${sonarToken} \
            -X
        """
    } else {
        println "Unsupported build tool: ${buildTool}"
    }
}

return this
