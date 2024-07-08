@Library('shared-libraries') _

pipeline {
    agent any

    parameters {
        string(name: 'GIT_URL', description: 'Git repository URL contains playbook')
        string(name: 'GIT_BRANCH_NAME', description: 'Git Branch name')
        credentials(name: 'GIT_CREDENTIAL', description: 'Jenkins credential for Git repo', required: true)
        string(name: 'REMOTE_HOST', description: 'Remote host IP')
        credentials(name: 'REMOTE_HOST_CREDENTIAL', description: 'Jenkins credential for connecting remote host', required: true)
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
                    NodejsDeployScript = loadScript('vars/common', 'ManualDeploy', 'DeployWithNodeJs')
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
       stage('Copy Repo to Remote') {
            steps {
               script {
                  NodejsDeployScript.copyRepoToRemote(params.REMOTE_HOST_CREDENTIAL, params.REMOTE_HOST)
            }
          }
       }
       stage('Deploy NodeJs in Remote') {
            steps {
               script {
                  NodejsDeployScript.deployWithNode(params.REMOTE_HOST_CREDENTIAL, params.REMOTE_HOST)
            }
          }
       }
    }
}
