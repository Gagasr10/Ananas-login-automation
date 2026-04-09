pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
        }
    }

    environment {
        CI = 'true'
        DOCKER = 'true'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    // Publish ExtentReports HTML report
                    publishHTML([
                        reportDir: 'extent-reports',
                        reportFiles: 'extent-report.html',
                        reportName: 'Extent Report',
                        keepAll: true
                    ])
                    // Publish JUnit XML reports
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        always {
            cleanWs() // Clean workspace after pipeline finishes
        }
        failure {
            echo ' Pipeline failed! Check the test reports.'
        }
        success {
            echo ' Pipeline succeeded! All tests passed.'
        }
    }
}