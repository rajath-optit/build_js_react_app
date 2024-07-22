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
        choice(name: 'CLOUD_PROVIDER', choices: ['AWS', 'Azure', 'GCP'], description: 'Cloud service provider')
        string(name: 'BUCKET_NAME', description: 'Bucket or Container name')
        string(name: 'ARTIFACT_FOLDER', description: 'The folder structure inside the bucket Ex: artifact', defaultValue: 'artifact')
        string(name: 'STORAGE_ACCOUNT_NAME', description: 'Azure Blob storage account name')
        choice(name: 'ARTIFACT_TYPE', choices: ['war', 'jar'], description: 'Artifact Type')
        string(name: 'ARTIFACT_PATH', defaultValue: 'target/*.jar', description: 'Artifact path where the artifact is being generated.')
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
                    artifactPublishScript = loadScript('vars/common', 'publishArtifacts', 'artifactsToCloud')
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

      stage('Uploading Artifact to S3 bucket') {
        when {
                expression { params.CLOUD_PROVIDER == 'AWS' }
            }
            steps {
               script {
                  artifactPublishScript.uploadToS3(params.ARTIFACT_PATH, params.BUCKET_NAME, params.ARTIFACT_FOLDER)
            }
          }
       }

      stage('Uploading Artifact to Blob storage') {
        when {
                expression { params.CLOUD_PROVIDER == 'Azure' }
            }
            steps {
               script {
                  def artifactName
            
                  if (params.ARTIFACT_TYPE == 'war') {
                      artifactName = "${env.JOB_NAME}/${env.BUILD_NUMBER}/application.war"
                  } else if (params.ARTIFACT_TYPE == 'jar') {
                      artifactName = "${env.JOB_NAME}/${env.BUILD_NUMBER}/application.jar"
                  } else {
                      error "Unsupported artifact type: ${params.ARTIFACT_TYPE}"
                  }
                 
                  artifactPublishScript.uploadToBlob(params.ARTIFACT_PATH, params.BUCKET_NAME, artifactName, params.STORAGE_ACCOUNT_NAME)
            }
          }
       }

      stage('Uploading Artifact to Cloud storage') {
        when {
                expression { params.CLOUD_PROVIDER == 'GCP' }
            }
            steps {
               script {
                  artifactPublishScript.uploadToGcloud(params.ARTIFACT_PATH, params.BUCKET_NAME, params.ARTIFACT_FOLDER)
            }
          }
       }       
    }
}
