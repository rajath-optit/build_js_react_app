**The description for the **config.xml** in the Jenkins job template:**

   - **7_deploy_kubernetes_dsl.xml**  : This pipeline script utilizes parameters (NAMESPACE, DEPLOYMENT_PATH, KUBE_CONFIG_CREDENTIAL, SERVICE_PATH, GIT_URL, BRANCH_NAME) configured in Jenkins to deploy your application to Kubernetes using '**kubectl**'.
   - **8_deploy_kubernetes_helm_dsl.xml**  :  This pipeline script utilizes parameters (NAMESPACE, IMAGE_TAG, KUBE_CONFIG_CREDENTIAL, IMAGE_NAME, GIT_URL, BRANCH_NAME, HELM_CHART_PATH, HELM_RELEASE_NAME) configured in Jenkins to deploy your application to Kubernetes using '**helm**'.
   - **8_deploy_kubernetes_helm_dsl_valuesfile.xml**  :  This pipeline script uses parameters (NAMESPACE, IMAGE_TAG, KUBE_CONFIG_CREDENTIAL, IMAGE_NAME, GIT_URL, BRANCH_NAME, HELM_CHART_PATH, HELM_RELEASE_NAME, Values_Yaml_Path) configured in Jenkins to deploy your application to Kubernetes using **'helm'** with **values.yaml**.

**Steps to create the job using config.xml:**

   - Create the directory named **"helm"** inside **/var/lib/jenkins/jobs**. Here, **"helm"** represents the job name.
   - Rename the file **8_deploy_kubernetes_helm_dsl_valuesfile.xml** to **config.xml**.
   - Copy **config.xml** to **/var/lib/jenkins/jobs/helm**.
   - Restart the Jenkins service.
