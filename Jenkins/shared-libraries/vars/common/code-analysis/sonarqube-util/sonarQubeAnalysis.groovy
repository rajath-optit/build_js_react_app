def call() {
    def scannerHome = tool 'SonarQubeScanner'
    withSonarQubeEnv('SonarQubeServer') {
        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=my_project_key \
                                              -Dsonar.sources=src \
                                              -Dsonar.java.binaries=build/classes \
                                              -Dsonar.host.url=http://your-sonarqube-server-url \
                                              -Dsonar.login=${env.SONAR_TOKEN}"
    }
}
