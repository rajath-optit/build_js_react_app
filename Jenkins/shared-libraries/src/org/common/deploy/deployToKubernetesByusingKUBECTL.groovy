package org.common.deploy
def deployToKubernetesByusingKUBECTL(kubeConfig, namespace, manifestPath) {
  echo "Kubeconfig: ${kubeConfig}"
  echo "Namespace: ${namespace}"
  echo "Manifest File Path: ${manifestPath}"

  // Deploy the application using kubectl commands
  sh """
    kubectl apply -f ${manifestPath} --namespace ${namespace}
  """
}


return this
