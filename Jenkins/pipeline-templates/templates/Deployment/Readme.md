### Kubernetes Deployment Pipeline Template (`K8sDeploy.groovy`):

1. **Configure Pipeline Parameters**:
   - Define the paths to the deployment YAML file (`DEPLOYMENT_PATH`) and the service YAML file (`SERVICE_PATH`).
   - Specify the Kubernetes namespace (`NAMESPACE`).
   - Provide the Jenkins credential for the Kubernetes config file (`KUBE_CONFIG_CREDENTIAL`).

2. **Usage**:
   - Create a new Jenkins pipeline job.
   - In the pipeline configuration, select "Pipeline script from SCM" and specify the repository containing the `K8sDeploy.groovy` file.
   - Configure the pipeline parameters according to your deployment requirements.
   - Run the pipeline job to deploy your application to Kubernetes.

### Helm Deployment Pipeline Template (`helmDeploy.groovy`):

1. **Configure Pipeline Parameters**:
   - Specify the path or name of the Helm chart to deploy (`HELM_CHART`).
   - Define the name of the Helm release (`RELEASE_NAME`).
   - Provide the Kubernetes namespace (`NAMESPACE`).
   - Provide the Jenkins credential for the Kubernetes config file (`KUBE_CONFIG_CREDENTIAL`).

2. **Usage**:
   - Create a new Jenkins pipeline job.
   - In the pipeline configuration, select "Pipeline script from SCM" and specify the repository containing the `helmDeploy.groovy` file.
   - Configure the pipeline parameters based on your Helm deployment requirements.
   - Run the pipeline job to deploy your application using Helm.

