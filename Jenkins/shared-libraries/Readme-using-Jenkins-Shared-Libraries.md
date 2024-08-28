# CICD/Jenkins-Shared-Libraries

This repository contains shared libraries for Jenkins pipelines to streamline common tasks and operations.

## Usage

### 1. Configure Jenkins

1. Navigate to **Manage Jenkins** > **Configure System**.
2. Under **Global Pipeline Libraries**, add a new library:
   - **Library Name**: shared-libraries
   - **Default Version**: main
   - **Retrieval Method**: Modern SCM
   - **Source Code Management**: Git
   - **Project Repository**: https://github.com/optimize-it/CICD.git

### 2. Import Shared Library in Jenkinsfile

@Library('shared-libraries') _

pipeline {
    agent any
    
    stages {
        stage('Example Stage') {
            steps {
                script {
                    // Call shared library functions here
                }
            }
        }
    }
}

### 3. Example of jenkins file Java with gradle 
@Library('shared-libraries') _

pipeline {
    agent any
    
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to build from')
        string(name: 'GIT_URL', defaultValue: 'please enter your git repo', description: 'Git repository URL')
        string(name: 'DOCKER_IMAGE_NAME', defaultValue: 'optit-lab-service', description: 'Docker image name')
        string(name: 'DOCKER_REPO', defaultValue: 'please enter your docker repo', description: 'Docker repository')
    }

    stages {
        stage('Git Checkout') {
            steps {
                script {
                    def gitCheckoutScript = load 'CICD/jenkins-shared-libraries/vars/common/scm-util/git/GitCheckout.groovy'
                    gitCheckoutScript.gitCheckout(params.BRANCH_NAME, params.GIT_URL, 'gitcredentails')
                }
            }
        }
        stage('Build with Gradle') {
            steps {
                script {
                    def buildWithGradleScript = load 'CICD/jenkins-shared-libraries/src/org/common/build/Java/BuildWithGradle.groovy'
                    buildWithGradleScript.buildWithGradle()
                }
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    def dockerBuildScript = load 'CICD/jenkins-shared-libraries/vars/common/docker-util/dockerBuild.groovy'
                    dockerBuildScript.dockerBuild(params.DOCKER_IMAGE_NAME)
                }
            }
        }
        stage('Docker Publish') {
            steps {
                script {
                    def dockerPublishScript = load 'CICD/jenkins-shared-libraries/vars/common/docker-util/dockerPublish.groovy'
                    dockerPublishScript.dockerPublish(params.DOCKER_IMAGE_NAME, params.DOCKER_REPO, 'dockercredentails')
                }
            }
        }
    }
}

Note: Git and Docker credentials should be configured by users in Jenkins,  Git and  Docker credentials ID should be specified accordingly in the Jenkins pipeline.
