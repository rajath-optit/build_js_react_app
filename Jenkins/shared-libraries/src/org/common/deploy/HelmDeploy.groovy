package org.common.deploy
def deploy(chartPath, releaseName, namespace, imageName, imageTag) {
    sh """
    helm upgrade --install ${releaseName} ${chartPath} \
        --namespace ${namespace} \
        --set image.repository=${imageName} \
        --set image.tag=${imageTag} \
        --wait
    """
}

return this
