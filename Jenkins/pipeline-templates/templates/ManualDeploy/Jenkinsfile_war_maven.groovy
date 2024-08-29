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
        string(name: 'REMOTE_HOST', description: 'Remote host IP')
        credentials(name: 'REMOTE_HOST_CREDENTIAL', description: 'Jenkins credential for connecting remote host', required: true)
        string(name: 'ARTIFACT_PATH', defaultValue: 'webapp/target/*.war', description: 'Artifact path where the artifact is being generated.')
        string(name: 'REMOTE_PATH', defaultValue: '/opt/tomcat/webapps/', description: 'The path where artifact to be copy.')
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
      
       stage('Deploy Maven war in Tomcat') {
            steps {
               script {
                  mavenDeployScript.deployWarToTomcat(params.REMOTE_HOST_CREDENTIAL, params.REMOTE_HOST, params.ARTIFACT_PATH, params.REMOTE_PATH)
            }
          }
       }
    }
}
