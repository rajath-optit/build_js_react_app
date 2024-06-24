@Library('shared-libraries') _

pipeline { agent any

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
                def gitCheckoutScript = load 'Jenkins/shared-libraries/vars/common/scm-util/git/GitCheckout.groovy'
                gitCheckoutScript.gitCheckout(params.BRANCH_NAME, params.GIT_URL, 'gitcredentails')
            }
        }
    }
    stage('Build with Gradle') {
        steps {
            script {
                def buildWithGradleScript = load 'Jenkins/shared-libraries/src/org/common/build/Java/BuildWithGradle.groovy'
                buildWithGradleScript.buildWithGradle()
            }
        }
    }
    stage('Docker Build') {
        steps {
            script {
                def dockerBuildScript = load 'Jenkins/shared-libraries/vars/common/docker-util/dockerBuild.groovy'
                dockerBuildScript.dockerBuild(params.DOCKER_IMAGE_NAME)
            }
        }
    }
    stage('Docker Publish') {
        steps {
            script {
                def dockerPublishScript = load 'Jenkins/shared-libraries/vars/common/docker-util/dockerPublish.groovy'
                dockerPublishScript.dockerPublish(params.DOCKER_IMAGE_NAME, params.DOCKER_REPO, 'dockercredentails')
            }
        }
    }
}
}
