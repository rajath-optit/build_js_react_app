@Library('shared-libraries') _

def gitCheckoutScript
def deployWithHelmScript

pipeline {
    agent any

    parameters {
        string(name: 'DSL_GIT_URL', defaultValue: 'https://github.com/optimize-it/CICD.git', description: 'Git repository URL for shared library')
        string(name: 'DSL_BRANCH_NAME', defaultValue: 'main', description: 'Shared Library Branch')
        string(name: 'GIT_SOURCE_CODE_URL', defaultValue: 'https://github.com/optit-cloud-team/optit-lab-service.git', description: 'Git repository URL for source code')
        string(name: 'SOURCE_CODE_BRANCH_NAME', defaultValue: 'main', description: 'Source Code Branch to build')
        string(name: 'HELM_CHART_PATH', defaultValue: 'helm/reactjs-app', description: 'Path to the Helm chart directory')
        string(name: 'NAMESPACE', defaultValue: 'devopshelm02', description: 'Kubernetes namespace')
        string(name: 'IMAGE_NAME', defaultValue: 'bharathoptdocker/reactjs', description: 'Docker image name')
        string(name: 'IMAGE_TAG', defaultValue: 'latest', description: 'Docker image tag')
        string(name: 'HELM_RELEASE_NAME', defaultValue: 'optit-reactapp', description: 'Helm release name')
        string(name: 'Values_Yaml_Path', defaultValue: 'helm/reactjs-app/values.yaml', description: 'valuesyaml')
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
                    deployWithHelmScript = load './cicd-repo/Jenkins/shared-libraries/vars/common/Deploy/HelmDeployWithValues.groovy'
                }
            }
        }

        stage('Git Checkout') {
            steps {
                script {
                    gitCheckoutScript.gitCheckout(params.SOURCE_CODE_BRANCH_NAME, params.GIT_SOURCE_CODE_URL, params.GIT_SOURCE_CODE_CREDENTIAL)
                }
            }
        }

        stage('Deploy to Kubernetes with Helm') {
            steps {
                script {
                    // Retrieve the kubeconfig file from Jenkins credentials
                    withCredentials([file(credentialsId: params.KUBE_CONFIG_CREDENTIAL, variable: 'KUBECONFIG_FILE')]) {
                        def kubeconfigDir = "${env.WORKSPACE}/.kube"
                        def kubeconfigPath = "${kubeconfigDir}/config"
                        sh """
                            mkdir -p ${kubeconfigDir}
                          
                        """
                          writeFile file: kubeconfigPath, text: sh(returnStdout: true, script: "cat ${KUBECONFIG_FILE}")
 
                        // Ensure proper permissions (chmod 600) on the kubeconfig file
                        sh "chmod 600 ${kubeconfigPath}"
                        
                        withEnv(["KUBECONFIG=${kubeconfigPath}"]) {
                            // Add debug statements to confirm the paths and environment variable
                            sh "echo 'Using kubeconfig at: ${kubeconfigPath}'"
                            sh "echo 'KUBECONFIG environment variable: ${env.KUBECONFIG}'"
                         
                        // Path to the Helm chart directory
                        def helmChartPath = "${env.WORKSPACE}/${params.HELM_CHART_PATH}"
                        def imageName = params.IMAGE_NAME
                        def imageTag = params.IMAGE_TAG
                       

                        // Call the deployWithHelm function from the shared library
                        deployWithHelmScript.deployWithHelm(helmChartPath, params.HELM_RELEASE_NAME, params.NAMESPACE, imageName, imageTag , params.Values_Yaml_Path)
                    
                        }    
                    }
                }
            }
        }
    }
}
