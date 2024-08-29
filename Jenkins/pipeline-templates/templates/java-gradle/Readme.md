# Java Gradle Templates

This directory contains Groovy templates for building and deploying Java projects with Gradle in Jenkins using Job DSL.


### Java Gradle DSL Pipeline

- **Description:** Template for a Jenkins pipeline job that builds and deploys a Java project using Gradle.
- **Usage:** Reference this template in your seed job to generate Jenkins pipeline jobs for Java Gradle projects.

## Usage

1. **Reference the Template in a Seed Job:**
   - In your seed job pipeline script, use the `readFileFromWorkspace` function to read the desired template file.
   - Example:
     ```groovy
     jobDsl scriptText: """
         pipelineJob('java-gradle-build-deploy') {
             description('Pipeline for building and deploying Java project with Gradle')

             definition {
                 cps {
                     script(readFileFromWorkspace('path/to/template.groovy'))
                     sandbox()
                 }
             }
         }
     ```
2. **Customize the Template Path:**
   - The `TEMPLATE_PATH` parameter in the seed job can be used to specify a custom path to the template file.

3. **Run the Seed Job:**
   - Ensure the seed job is configured with the correct parameters and run it to generate the desired Jenkins pipeline jobs for Java Gradle projects.

