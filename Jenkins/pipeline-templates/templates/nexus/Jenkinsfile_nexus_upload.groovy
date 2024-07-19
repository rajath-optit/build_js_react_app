@Library('shared-libraries') _

pipeline {
    agent any

tools {
        // Install the Maven version configured as "maven" and add it to the path.
        maven "maven"
}

    parameters {
        string(name: 'GIT_URL', description: 'Git repository URL contains Source Code')
        string(name: 'GIT_BRANCH_NAME', description: 'Git Branch name')
        credentials(name: 'GIT_CREDENTIAL', description: 'Jenkins credential for Git repo', required: true)
        string(name: 'ARTIFACT_ID', description: 'Artifact Id')
        string(name: 'ARTIFACT_PATH', description: 'Artifact Path where it generates', defaultValue: 'target/*.war')
        choice(name: 'ARTIFACT_TYPE', choices: ['war', 'jar'], description: 'Artifact Type')
        string(name: 'NEXUS_URL', description: 'Nexus URL IP:Port')
        credentials(name: 'NEXUS_CREDENTIAL_ID', description: 'Jenkins credential for connecting nexus UI', required: true)
        string(name: 'NEXUS_REPOSITORY', description: 'Nexus Repository where we need to store the artifacts in nexus UI')
        string(name: 'NEXUS_GROUP_ID', description: 'Nexus folder structure inside the repository')
    }

    stages {
        stage('Load Scripts') {
            steps {
                script {
                    // Define baseDir for Jenkins shared libraries
                    def baseDir = "${env.WORKSPACE}/Jenkins/shared-libraries"

                    // Load scripts using dynamically constructed paths
                    def loadScript = { type, technology, scriptName ->
                        def scriptPath = "${baseDir}/${type}/${technology}/${scriptName}.groovy"
                        return load(scriptPath)
                    }

                    // Load scripts from 'vars' directory
                    gitCheckoutScript = loadScript('vars/common/scm-util', 'git', 'GitCheckout')
                    mavenDeployScript = loadScript('vars/common', 'ManualDeploy', 'DeployWithMaven')
                }
            }
        }

        stage('Git Checkout') {
            steps {
                script {
                    gitCheckoutScript.gitCheckout(params.GIT_BRANCH_NAME, params.GIT_URL, params.GIT_CREDENTIAL)
                }
            }
        }

       stage('Package') {
            steps {
               script {
                  mavenDeployScript.deployWithMaven()
            }
          }
       }
      
       stage('Copy Artifact to nexus') {
            steps {
               script {
                  def version = env.BUILD_NUMBER   
                  nexusArtifactUploader artifacts: [[artifactId: params.ARTIFACT_ID, classifier: '', file: params.ARTIFACT_PATH, type: params.ARTIFACT_TYPE]], credentialsId: params.NEXUS_CREDENTIAL_ID, groupId: params.NEXUS_GROUP_ID, nexusUrl: params.NEXUS_URL, nexusVersion: 'nexus3', protocol: 'http', repository: params.NEXUS_REPOSITORY, version: version
            }
          }
       }
    }
}
