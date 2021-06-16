pipeline {
  agent any
  stages {
    stage('Build'){
      steps {
        sh '/var/lib/jenkins/tools/hudson.plugins.gradle.GradleInstallation/gradleversion/bin/gradle --scan -s build' 
      }
    }
    stage('Deploy') {
      when {
            expression { env.BRANCH_NAME == 'master' }
            }
      steps {
        sh 'who'
        sh 'cp build/libs/BeatHubApi-0.0.1-SNAPSHOT.war /opt/tomcat/apache-tomcat-9.0.44/webapps/'
      }
    }
  }
}
