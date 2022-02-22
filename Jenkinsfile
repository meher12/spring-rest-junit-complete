pipeline {
  agent { label 'linux' }
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  
 //triggers {
       // poll repo every  minute for changes
   //    pollSCM('* * * * *')
  //}
  
  stages {
    stage('SCM') {
        steps {
            properties([
               pipelineTriggers([
                  pollSCM('* * * * *')
                ]) 
            ])
          checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'kpiswiseserver', url: 'https://github.com/meher12/spring-rest-junit-complete.git']]])
        }
    }
    
    stage('Build'){
        steps {
          echo "-=- compiling project -=-"
          sh 'mvn clean compile'
         }
    }
    
     stage('Unit Test'){
        steps {
          echo "-=- execute unit tests -=-"
          sh 'mvn test'
         }
        post {
              always {
                    junit '**/target/surefire-reports/TEST-*.xml'
              }
        }
    }
    
    stage('Scan') {
      steps {
        withSonarQubeEnv(installationName: 'sonarjenckis') { 
             echo "-=- execute Sonarqube Scan -=-"
            sh 'mvn clean package  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml sonar:sonar'
        }
      }
    }
    
    stage("Quality Gate"){
        steps {
           timeout(time: 2, unit: 'MINUTES') {
            script {
               echo "-=- Get Sonarqube Quality Gate -=-"
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