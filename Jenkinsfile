pipeline {

    agent any

    environment {
        REPORT_DIR = "${WORKSPACE}\\reports"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Pull Image') {
            steps {
                bat 'docker pull ubuntu:22.04'
            }
        }

        stage('Run Tests') {
            steps {

                bat '''
                if not exist "%REPORT_DIR%" mkdir "%REPORT_DIR%"

                docker run --rm ^
                -v "%WORKSPACE%:/app" ^
                -v "%REPORT_DIR%:/app/target/surefire-reports" ^
                -w /app ^
                ubuntu:22.04 bash -c "
                apt update &&
                apt install openjdk-17-jdk -y &&
                apt install maven -y &&
                mvn test
                "
                '''
            }
        }

        stage('Publish Results') {
            steps {
                junit 'reports/*.xml'
            }
        }
    }

    post {

        always {
            archiveArtifacts artifacts: 'reports/*.xml'
        }

        success {
            echo 'BUILD PASSED'
        }

        failure {
            echo 'BUILD FAILED'
        }
    }
}