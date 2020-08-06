pipeline {
    agent any

    stage('Build') {
        steps {
            sh './mvnw compile'
        }
    }

    stage('Test') {
        steps {
            sh './mvnw test'
        }
    }
}
