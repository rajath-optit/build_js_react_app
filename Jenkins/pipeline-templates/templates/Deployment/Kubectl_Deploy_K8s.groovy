@Library('shared-libraries') _

def gitCheckoutScript
def kubernetesDeployScript

pipeline {
    agent any

    parameters {
        string(name: 'DSL_GIT_URL', defaultValue: 'https://github.com/optimize-it/CICD.git', description: 'Git repository URL for shared library')
        string(name: 'DSL_BRANCH_NAME', defaultValue: 'main', description: 'Shared Library Branch')
        string(name: 'GIT_SOURCE_CODE_URL', defaultValue: 'https://github.com/optit-cloud-team/optit-lab-service.git', description: 'Git repository URL for source code')
        string(name: 'SOURCE_CODE_BRANCH_NAME', defaultValue: 'master', description: 'Source Code Branch to build')
        string(name: 'DEPLOYMENT_PATH', defaultValue: 'kubernetes/manifest/deployment.yaml', description: 'Path to the deployment YAML file')
        string(name: 'SERVICE_PATH', defaultValue: 'kubernetes/manifest/service.yaml', description: 'Path to the service YAML file')
        string(name: 'NAMESPACE', defaultValue: 'devopskubectl01', description: 'Kubernetes namespace')
        credentials(name: 'KUBE_CONFIG_CREDENTIAL', description: 'Jenkins credential for Kubernetes config file (kubeconfig)', defaultValue: '', required: true)
        credentials(name: 'GIT_DSL_CREDENTIAL', description: 'Jenkins credential for shared library Git repo', defaultValue: 'bkgit', required: true)
        credentials(name: 'GIT_SOURCE_CODE_CREDENTIAL', description: 'Jenkins credential for source code Git repo', defaultValue: 'git-PAT', required: true)
    }

    stages {
        stage('Git Checkout DSL Repo') {
            steps {
                script {
                    dir('cicd-repo') {
                        git branch: params.DSL_BRANCH_NAME,
                            credentialsId: params.GIT_DSL_CREDENTIAL,
                            url: params.DSL_GIT_URL
                    }
                }
            }
        }

        stage('Load DSL') {
            steps {
                script {
                    gitCheckoutScript = load './cicd-repo/Jenkins/shared-libraries/vars/common/scm-util/git/GitCheckout.groovy'
                    kubernetesDeployScript = load './cicd-repo/Jenkins/shared-libraries/vars/common/Deploy/deployToKubernetesByusingKUBECTL.groovy'
                }
            }
        }

        stage('Git Checkout Source Code Repo') {
            steps {
                script {
                    gitCheckoutScript.gitCheckout(params.SOURCE_CODE_BRANCH_NAME, params.GIT_SOURCE_CODE_URL, params.GIT_SOURCE_CODE_CREDENTIAL)
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Retrieve the kubeconfig file from Jenkins credentials
                    withCredentials([file(credentialsId: params.KUBE_CONFIG_CREDENTIAL, variable: 'KUBECONFIG_FILE')]) {
                        def kubeconfigPath = "${env.WORKSPACE}/.kube/config"
                        sh """
                            mkdir -p ${env.WORKSPACE}/.kube
                      
                        """
                        writeFile file: kubeconfigPath, text: sh(returnStdout: true, script: "cat ${KUBECONFIG_FILE}")
 
                        // Ensure proper permissions (chmod 600) on the kubeconfig file
                        sh "chmod 600 ${kubeconfigPath}"
                        
                        withEnv(["KUBECONFIG=${kubeconfigPath}"]) {
                            // Add debug statements to confirm the paths and environment variable
                            sh "echo 'Using kubeconfig at: ${kubeconfigPath}'"
                            sh "echo 'KUBECONFIG environment variable: ${env.KUBECONFIG}'"
                            
                        // Call the deploy function from the shared library with appropriate arguments
                        def deploymentPath = "${env.WORKSPACE}/${params.DEPLOYMENT_PATH}"
                        def servicePath = "${env.WORKSPACE}/${params.SERVICE_PATH}"
                        
                        kubernetesDeployScript.deployToKubernetesByusingKUBECTL(kubeconfigPath, params.NAMESPACE, deploymentPath, servicePath)
                        }    
                    }
                }
            }
        }
    }
}
