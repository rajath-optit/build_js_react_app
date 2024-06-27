package org.common.deploy
def deploy(chartPath, releaseName, namespace, imageName, imageTag, valuesFile) {
    sh """
    helm upgrade --install ${releaseName} ${chartPath} \
        --namespace ${namespace} \
        --set image.repository=${imageName} \
        --set image.tag=${imageTag} \
        --values ${valuesFile} \
        --wait
    """
}
 
return this
