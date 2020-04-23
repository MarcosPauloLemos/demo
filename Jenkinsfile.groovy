pipeline {
    agent any
    stages {
        stage ('checkout') {
            step {
                sh 'git pull origin master'
            }
        }

        stage ('build') {
            step {
                sh './gradlew clean build -X test'
            }
        }

        stage ('test') {
            step {
                sh './gradlew test'
            }
        }

        stage ('docker') {
            step {
                sh 'docker build -t localhost:5000/demo:1 .'
                sh 'docker push localhost:5000/demo:1'
            }
        }

        stage ('deploy') {
            step {
                sh 'docker run -d -p 9081:9081 localhost:5000/demo:1'
            }
        }
    }
}