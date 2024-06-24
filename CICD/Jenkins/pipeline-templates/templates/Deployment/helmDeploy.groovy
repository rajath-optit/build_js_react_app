@Library('shared-libraries') _

def gitCheckoutScript
def deployWithHelmScript

pipeline {
    agent any

    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to build from')
        string(name: 'GIT_URL', defaultValue: 'https://github.com/optit-cloud-team/optit-lab-service.git', description: 'Git repository URL')
        string(name: 'HELM_CHART_PATH', defaultValue: 'helm/optit-lab-service/', description: 'Path to the Helm chart directory')
        string(name: 'NAMESPACE', defaultValue: 'optit-lab', description: 'Kubernetes namespace')
        string(name: 'IMAGE_NAME', defaultValue: 'bharathoptdocker/optit-lab-service', description: 'Docker image name')
        string(name: 'IMAGE_TAG', defaultValue: 'latest', description: 'Docker image tag')
        string(name: 'HELM_RELEASE_NAME', defaultValue: 'optit-lab-service', description: 'Helm release name')
        credentials(name: 'KUBE_CONFIG_CREDENTIAL', description: 'Jenkins credential for Kubernetes config file (kubeconfig)', defaultValue: '', required: true)
    }

    stages {
        stage('Git Checkout CICD Repo') {
            steps {
                script {
                    dir('cicd-repo') {
                        git branch: 'main',
                            credentialsId: 'bkgit',
                            url: 'https://github.com/optimize-it/CICD.git'
                    }
                }
            }
        }

        stage('Load DSL') {
            steps {
                script {
                    gitCheckoutScript = load './cicd-repo/Jenkins/shared-libraries/vars/common/scm-util/git/GitCheckout.groovy'
                    deployWithHelmScript = load './cicd-repo/Jenkins/shared-libraries/vars/common/Deploy/HelmDeploy.groovy'
                }
            }
        }

        stage('Git Checkout') {
            steps {
                script {
                    gitCheckoutScript.gitCheckout(params.BRANCH_NAME, params.GIT_URL, 'bharath')
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

                        // Path to the Helm chart directory
                        def helmChartPath = "${env.WORKSPACE}/${params.HELM_CHART_PATH}"
                        def imageName = params.IMAGE_NAME
                        def imageTag = params.IMAGE_TAG

                        // Call the deployWithHelm function from the shared library
                        deployWithHelmScript.deployWithHelm(helmChartPath, params.HELM_RELEASE_NAME, params.NAMESPACE, imageName, imageTag)
                    }
                }
            }
        }
    }
}
