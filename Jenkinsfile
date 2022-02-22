pipeline {
  agent { label 'linux' }
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  stages {
    stage('SCM') {
        steps {
          checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'kpiswiseserver', url: 'https://github.com/meher12/spring-rest-junit-complete.git']]])
        }
    }
    
    stage('Build'){
        
         sh 'mvn clean compile'
    }
    
     stage('Unit Test'){
        
        sh 'mvn test'
         
        post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
        }
    }
    
    stage('Scan') {
      steps {
        withSonarQubeEnv(installationName: 'sonarjenckis') { 
            sh 'mvn clean package sonar:sonar'
        }
      }
    }
    
    stage("Quality Gate"){
        steps {
           timeout(time: 2, unit: 'MINUTES') {
            script {
              def qg = waitForQualityGate()
                 if (qg.status != 'OK') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                  }
             }
             
            //waitForQualityGate abortPipeline: true
           }
        }
    }
    
    
    
    
    
  }
}