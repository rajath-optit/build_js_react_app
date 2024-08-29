# SonarCloud Analysis Function

## Description

The `sonarCloudAnalysis` function performs a SonarCloud analysis on your project. It reads SonarQube properties from a specified properties file, updates or adds SonarQube configurations in the `build.gradle` file, and runs the SonarQube scanner to analyze the project.


Prerequisites:

1. **Generate SonarCloud Authentication Token:**
   - Log in to [SonarCloud](https://sonarcloud.io/).
   - Navigate to your account settings.
   - Generate a new authentication token.

2. Log in to your Jenkins instance.
Go to "Manage Jenkins" > "Configure System".
Scroll down to the "SonarQube servers" section.
Check the box to enable job administrators to inject SonarQube server configuration as environment variables in the build.
Add SonarCloud server configuration:
Name: SonarCloud
Server URL: https://sonarcloud.io/
Server authentication token: [Your SonarCloud authentication token]
Save the configuration.

Jenkins, navigate to "Manage Jenkins" > "Global Tool Configuration

Look for "SonarQube Scanner installations" section.
Add a SonarQube Scanner installation:
Name: SonarQube Scanner
Install from: Choose the appropriate method (e.g., "Install automatically").
Save the configuration.

## Parameters

- **toolName**: The name of the SonarQube scanner tool as configured in Jenkins global tool configuration. This is used to locate the SonarQube scanner executable.
- **sonarPropertiesFilePath**: The path to the SonarQube properties file (`sonar-project.properties`) that contains the project-specific SonarQube configuration.

## What It Does

1. **Locate SonarQube Scanner**: Uses the specified `toolName` to locate the SonarQube scanner tool in Jenkins.
2. **Read Properties File**: Reads the `sonar-project.properties` file to extract SonarQube configurations such as `projectKey`, `organization`, `sources`, `binaries`, `host.url`, and `login`.
3. **Update or Add SonarQube Configuration in `build.gradle`**:
    - Checks if a `sonarqube` block exists in the `build.gradle` file.
    - If it exists, updates the `sonar.host.url` and `sonar.login` properties.
    - If it does not exist, appends a new `sonarqube` block with the necessary properties.
4. **Run SonarQube Scanner**: Executes the SonarQube scanner with the configurations read from the properties file.

## How to Use in a Jenkins Pipeline

### Example Pipeline:

```groovy
pipeline {
    agent any
   parameters {
        string(name: 'toolName', defaultValue: 'mysonar', description: 'The name of the SonarQube scanner tool as configured in Jenkins global tool configuration. This is used to locate the SonarQube scanner executable')
        string(name: 'propertiesFilePath', defaultValue: 'sonar-project.properties', description: 'Sonar propertiesfile')
        
    }
    stages {
        stage('Prepare') {
            steps {
                script {
                    // Define the properties file path and tool name
                    def propertiesFilePath = 'sonar-project.properties' // Replace with your properties file path if different
                    def toolName = 'mysonar' // Replace 'mysonar' with the name of your SonarQube installation
                   // propertiesFilePath,toolName pass as a parameter
                    // Call the sonarCloudAnalysis function with the properties file path and tool name
                    sonarCloudAnalysis(toolName, propertiesFilePath)


                }
            }
        }


## The method named sonarqubeWithPropertiesFile.groovy extracts inputs from a properties file, while the method named sonarCloudAnalysis.groovy takes inputs as parameters.
    }
}
