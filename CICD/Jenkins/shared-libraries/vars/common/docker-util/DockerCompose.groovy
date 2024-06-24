// jenkins-shared-libraries/vars/common/docker-util/DockerCompose.groovy

def call(String composeFile) {
    // Define the directory containing the docker-compose.yml file
    def composeDir = "${WORKSPACE}/${composeFile.parent}"

    // Function to start Docker Compose services
    def startServices = {
        echo "Starting Docker Compose services from ${composeFile}"
        sh "docker-compose -f ${composeFile} -d up"
    }

    // Function to stop Docker Compose services
    def stopServices = {
        echo "Stopping Docker Compose services from ${composeFile}"
        sh "docker-compose -f ${composeFile} down"
    }

    // Return closure for method chaining
    return [startServices: startServices, stopServices: stopServices]
}
