pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                echo 'Building project...'
                sh './mvnw clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging application...'
                sh './mvnw package'
            }
        }
    }
}