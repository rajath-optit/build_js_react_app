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
        string(name: 'ARTIFACT_PATH', description: 'Artifact Path where it generates', defaultValue: 'target/application.war')
        choice(name: 'ARTIFACT_TYPE', choices: ['war', 'jar'], description: 'Artifact Type')
        string(name: 'NEXUS_URL', description: 'Nexus URL IP:Port Ex:172.x.x.1:8081')
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
                    nexusScript = loadScript('vars/common', 'nexus', 'nexusUpload')
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
                  nexusScript.nexusUpload()
          }
       }
    }
}
}
