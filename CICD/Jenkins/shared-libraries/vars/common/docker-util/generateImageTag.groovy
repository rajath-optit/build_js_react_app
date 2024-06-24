def generateImageTag(String imageName) {
  // Combine Git commit hash (optional) and date/time
  def version = sh(returnStdout: true, script: "git rev-parse --short HEAD || echo ''")?.trim() + "-" + date + '%Y%m%d_%H%M'

  // Construct and return the image tag
  return "${imageName}:${version}"
}
