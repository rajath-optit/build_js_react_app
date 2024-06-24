pipeline {
    agent any

    parameters {
        string(name: 'JOB_NAME', defaultValue: 'generated-java-sonar_with_propertiesFile', description: 'Name of the generated job')
        string(name: 'TEMPLATE_PATH', defaultValue: 'Jenkins/pipeline-templates/templates/sonar_wth_properties_file/java-gradle-sonar-cloud-dsl-pipeline_with_propertiesFile.groovy', description: 'Path to the Groovy template file')
        string(name: 'CREDENTIALS_ID', defaultValue: 'bkgit', description: 'Credentials ID for Git checkout')
    }

    stages {
        stage('Git Checkout CICD Repo') {
            steps {
                script {
                    dir('cicd-repo') {
                        git branch: 'main',
                            credentialsId: params.CREDENTIALS_ID,
                            url: 'https://github.com/optimize-it/CICD.git'
                    }
                }
            }
        }

        stage('Generate Pipeline Job') {
            steps {
                script {
                    def jobName = params.JOB_NAME
                    def templatePath = params.TEMPLATE_PATH
                    jobDsl scriptText: """
                        pipelineJob('${jobName}') {
                            description('Pipeline for Java with Sonar Cloud analysis using a properties file')

                            definition {
                                cps {
                                    script(readFileFromWorkspace('${templatePath}'))
                                    sandbox()
                                }
                            }
                        }
                    """
                }
            }
        }
    }
}
