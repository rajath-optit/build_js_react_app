
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
        string(name: 'SONAR_PROJECT_KEY', defaultValue: 'myorganisation2_myproject2', description: 'SonarQube project key')
        string(name: 'SONAR_ORGANIZATION', defaultValue: 'myorganisation2', description: 'SonarQube organization key')
        string(name: 'SONAR_SOURCES_DIR', defaultValue: 'src', description: 'Source code directory for SonarQube analysis')
        string(name: 'SONAR_BINARIES_DIR', defaultValue: 'build/classes', description: 'Binary directory for SonarQube analysis')
        string(name: 'SONAR_TOKEN', defaultValue: 'ce67848db2f41c69d4a11bc021d63362ebd70d22', description: 'SonarQube authentication token')
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
                    sonarScript = load './cicd-repo/Jenkins/shared-libraries/vars/common/code-analysis/sonarqube-util/sonarCloudAnalysis.groovy'
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
                    sonarScript.sonarCloudAnalysis(
                        params.SONAR_PROJECT_KEY,
                        params.SONAR_ORGANIZATION,
                        params.SONAR_SOURCES_DIR,
                        params.SONAR_BINARIES_DIR,
                        params.SONAR_TOKEN
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
