# Seed Job Pipeline

This Jenkins pipeline job is designed to create another pipeline job for Java with Sonar Cloud analysis using a properties file. It allows dynamic configuration of the job name, template path, and credentials for Git checkout.

## Parameters

- **JOB_NAME**: Name of the generated job.
  - Default: `generated-java-sonar_with_propertiesFile`
  - Description: The name to be used for the newly generated pipeline job.

- **TEMPLATE_PATH**: Path to the Groovy template file for the generated pipeline job.
  - Default: `Jenkins/pipeline-templates/templates/sonar_wth_properties_file/java-gradle-sonar-cloud-dsl-pipeline_with_propertiesFile.groovy`
  - Description: Path to the Groovy template file used to define the generated pipeline job.

- **CREDENTIALS_ID**: Credentials ID for Git checkout.
  - Default: `bkgit`
  - Description: Jenkins credentials ID used for accessing the Git repository.

## Usage

1. **Create the Seed Job:**
   - In Jenkins, create a new pipeline job.
   - Copy the pipeline script into the job’s pipeline configuration.

2. **Configure Parameters:**
   - Set the `JOB_NAME`, `TEMPLATE_PATH`, and `CREDENTIALS_ID` parameters as needed.
   - The default values are provided but can be adjusted based on your setup.

3. **Run the Seed Job:**
   - Go to the job page in Jenkins.
   - Click on **Build with Parameters**.
   - Ensure the parameters are set correctly, then click **Build**.

This seed job will check out the CICD repository from GitHub and use the specified Groovy template file to create a new pipeline job for Java with Sonar Cloud analysis.
