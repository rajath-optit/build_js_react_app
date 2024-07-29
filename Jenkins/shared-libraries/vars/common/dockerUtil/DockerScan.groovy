// vars/common/containerPublish/dockerScan.groovy

def dockerScan(imageName) {
    // Scan the Docker image using Trivy
    sh """
       
        # Run the Trivy scan
        echo "Running Trivy scan on Docker image: ${imageName}"
        trivy image --exit-code 0 --severity HIGH,CRITICAL ${imageName} || true
    """
}

return this
