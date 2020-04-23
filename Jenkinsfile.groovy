pipeline {
    agent any
    stages {
        stage ('checkout') {
            steps {
                sh 'git pull origin master'
            }
        }

        stage ('build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage ('docker') {
            steps {
                sh 'docker build -t localhost:5000/demo:1 .'
                sh 'docker push localhost:5000/demo:1'
            }
        }

        stage ('deploy') {
            steps {
                sh 'docker run -d -p 9081:9081 localhost:5000/demo:1'
            }
        }
    }
}