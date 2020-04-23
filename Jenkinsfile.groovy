pipeline {
    agent any
    stages {
        stage ('checkout') {
            sh 'git pull origin master'
        }

        stage ('build') {
            sh './gradlew clean build -X test'
        }

        stage ('test') {
            sh './gradlew test'
        }

        stage ('docker') {
            sh 'docker build -t localhost:5000/demo:1 .'
            sh 'docker push localhost:5000/demo:1'
        }

        stage ('deploy') {
            sh 'docker run -d -p 9081:9081 localhost:5000/demo:1'
        }
    }
}