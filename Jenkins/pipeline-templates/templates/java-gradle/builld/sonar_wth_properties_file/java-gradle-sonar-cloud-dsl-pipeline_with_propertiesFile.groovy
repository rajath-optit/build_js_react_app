@Library('shared-libraries') _

def gitCheckoutScript
def buildWithGradleScript
def sonarScript
def dockerBuildScript
def dockerPublishScript

pipeline {
    agent any

    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to build from')
        string(name: 'GIT_URL', defaultValue: 'https://github.com/optit-cloud-team/optit-lab-service.git', description: 'Git repository URL')
        string(name: 'DOCKER_IMAGE_NAME', defaultValue: 'optit-lab-service', description: 'Docker image name')
        string(name: 'DOCKER_REPO', defaultValue: 'please enter your docker repo', description: 'Docker repository')
        string(name: 'ToolName', defaultValue: 'mysonar', description: '')
        string(name: 'sonarPropertiesFilePath', defaultValue: 'project_sonar.propeties', description: 'SonarProperties file')
       
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
                    buildWithGradleScript = load './cicd-repo/Jenkins/shared-libraries/src/org/common/build/Java/BuildWithGradle.groovy'
                    sonarScript = load './cicd-repo/Jenkins/shared-libraries/vars/common/code-analysis/sonarqube-util/sonarqubeWithPropertiesFile.groovy'
                    dockerBuildScript = load './cicd-repo/Jenkins/shared-libraries/vars/common/docker-util/DockerBuild.groovy'
                    dockerPublishScript = load './cicd-repo/Jenkins/shared-libraries/vars/common/docker-util/DockerPublish.groovy'
                }
            }
        }

        stage('Git Checkout') {
            steps {
                script {
                    gitCheckoutScript.gitCheckout(params.BRANCH_NAME, params.GIT_URL, 'bkgit')
                }
            }
        }

        stage('Build with Gradle') {
            steps {
                script {
                    buildWithGradleScript.buildWithGradle()
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sonarScript.sonarqubeWithPropertiesFile(
                        params.ToolName,
                        params.sonarPropertiesFilePath                       
                    )
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    dockerBuildScript.dockerBuild(params.DOCKER_IMAGE_NAME)
                }
            }
        }

        stage('Docker Publish') {
            steps {
                script {
                    dockerPublishScript.dockerPublish(params.DOCKER_IMAGE_NAME, params.DOCKER_REPO, 'bkdockerid')
                }
            }
        }
    }
}
