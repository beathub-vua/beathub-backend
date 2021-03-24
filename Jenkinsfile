pipeline {
  agent any
  stages {
    stage('Build'){
      steps {
        sh '/var/lib/jenkins/tools/hudson.plugins.gradle.GradleInstallation/gradleversion/bin/gradle --scan -s build' 
      }
    }
    stage('Deploy') {
      steps { 
        sh 'ls' 
      }
    }
  }
}