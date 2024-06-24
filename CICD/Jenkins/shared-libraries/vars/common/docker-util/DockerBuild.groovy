def dockerBuild(String imageName, String imageTag) {
    sh "docker build -t ${imageName}:${imageTag} ."
}

return this
