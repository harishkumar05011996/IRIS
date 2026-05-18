pipeline {
  agent any

triggers {
    cron('TZ=Asia/Kolkata\n30 10 * * 1-5')
  }

  stages {
    stage('Checkout') {
      steps {
        // Recommended checkout method:
        checkout scmGit(
          branches: [[name: 'master']],
          userRemoteConfigs: [[url: 'https://github.com/harishkumar05011996/IRIS.git']]
        )
      }
    }

    stage('Build & Test') {
      steps {
        // Windows agent:
        bat 'mvn -v'
        bat 'mvn clean test'
      }
    }
  }

  post {
    always {
      // Publish the Extent HTML report in Jenkins
      publishHTML(target: [
        allowMissing: false,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: 'src/main/resources/extentedReports',
        reportFiles: 'IRISExtentedReport.html',
        reportName: 'Extent Report'
      ])

      // Archive report folder (downloadable)
      archiveArtifacts artifacts: 'src/main/resources/extentedReports/**', allowEmptyArchive: true
    }

    success {
      // Email report after successful build
      emailext(
        to: 'harish.kumar02@irissoftware.com',
        subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER} - Extent Report",
        mimeType: 'text/html',
        body: """
          <p><b>Build:</b> ${env.JOB_NAME} #${env.BUILD_NUMBER}</p>
          <p><b>Status:</b> ${currentBuild.currentResult}</p>
          <p><b>Build URL:</b> ${env.BUILD_URL}${env.BUILD_URL}</a></p>
          <p>Extent report is published in Jenkins and attached as artifacts.</p>
        """,
        attachmentsPattern: 'src/main/resources/extentedReports/**'
      )
    }
  }
}