def dockerBuild(String imageName, String tag) {
    sh "docker build -t ${imageName}:${tag} ."
}
return this
