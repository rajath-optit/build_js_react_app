def dockerScan(String imageName) {
    sh """
        trivy image ${imageName} || exit 1
    """
}
return this
