# Jenkins Pipeline for Continuous Integration and Deployment

This Jenkins pipeline automates the continuous integration and deployment process for your project.

## Prerequisites

1. Jenkins installed and configured.
2. Git installed.
3. Docker installed (if Docker image build and publish stages are used).
4. SonarQube server set up (if SonarQube analysis stage is used).

## Setup

1. Create a new Jenkins pipeline job.
2. Configure the job to use this pipeline script.
3. Set up the necessary Jenkins credentials:
   - Git credentials (`bkgit`).
   - Docker credentials (if Docker image push is required).
   - SonarQube authentication token (`ce67848db2f41c69d4a11bc021d63362ebd70d22`).
4. Customize the parameters in the pipeline job configuration as needed:
   - `BRANCH_NAME`: Branch to build from.
   - `GIT_URL`: Git repository URL.
   - `DOCKER_IMAGE_NAME`: Docker image name.
   - `DOCKER_REPO`: Docker repository URL.
   - `SONAR_PROJECT_KEY`: SonarQube project key.
   - `SONAR_ORGANIZATION`: SonarQube organization key.
   - `SONAR_SOURCES_DIR`: Source code directory for SonarQube analysis.
   - `SONAR_BINARIES_DIR`: Binary directory for SonarQube analysis.

## Pipeline Stages

1. **Git Checkout CICD Repo**: Clones the CI/CD repository containing shared pipeline libraries.
2. **Load DSL**: Loads the shared DSL scripts for Git checkout, Gradle build, SonarQube analysis, Docker build, and Docker publish.
3. **Git Checkout**: Checks out the specified branch from the Git repository.
4. **Build with Gradle**: Builds the project using Gradle.
5. **SonarQube Analysis**: Performs static code analysis using SonarQube.
6. **Docker Build**: Builds the Docker image.
7. **Docker Publish**: Publishes the Docker image to the specified repository.
