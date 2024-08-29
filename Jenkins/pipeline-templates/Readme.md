# Templates

This directory contains Groovy templates for creating Jenkins jobs using Job DSL. These templates can be referenced in your seed job to dynamically generate Jenkins pipeline jobs.

## Available Templates

### Java-Gradle-Sonar-Cloud-Pipeline

- **Description:** Template for a Jenkins pipeline job that performs Sonar Cloud analysis using a properties file for a Java project built with Gradle.
- **Usage:** Reference this template in your seed job to generate Jenkins pipeline jobs.

## Usage

1. **Reference the Template in a Seed Job:**
   - In your seed job pipeline script, use the `readFileFromWorkspace` function to read the desired template file.
   - Example:
     ```groovy
     jobDsl scriptText: """
         pipelineJob('generated-java-sonar_with_propertiesFile') {
             description('Pipeline for Java with Sonar Cloud analysis using a properties file')

             definition {
                 cps {
                     script(readFileFromWorkspace('path/to/template.groovy'))
                     sandbox()
                 }
             }
         }
     """
     ```

2. **Customize the Template Path:**
   - The `TEMPLATE_PATH` parameter in the seed job can be used to specify a custom path to the template file.

3. **Run the Seed Job:**
   - Ensure the seed job is configured with the correct parameters and run it to generate the desired Jenkins jobs.
# Jobs

This directory contains example Jenkins job configurations that can be used as references or starting points for creating your own Jenkins jobs. These jobs demonstrate various CI/CD pipelines and best practices.

## Available Jobs

### Kubernetes-Helm-Deployment

- **Description:** Example of a Jenkins job for deploying applications to Kubernetes using Helm.
- **Purpose:** This job can be used as a reference for setting up Kubernetes deployments with Helm in Jenkins.

## Usage

1. **View Example Job Configurations:**
   - Browse the job configurations in this directory to understand the pipeline steps and best practices.

2. **Create Similar Jobs:**
   - Use the example configurations as a reference to create similar jobs in your Jenkins instance.

3. **Import Job Configurations:**
   - If needed, you can import these job configurations directly into Jenkins by copying the XML content and pasting it into the Jenkins job configuration page (or using Jenkins Job DSL to create similar jobs programmatically).

## Reference in Seed Jobs

- To generate similar jobs using a seed job, refer to the example job configurations and create corresponding templates or DSL scripts in the `templates` directory.
- Use these templates in your seed jobs to dynamically generate Jenkins jobs based on the provided examples.

