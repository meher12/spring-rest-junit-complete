pipeline {

  agent { label 'linux' }
  
  // --Changes in build triggers in scripted pipelines are only seen by Jenkins after the changed pipeline ran.
//  ---Note: Verify on the Jenkins master, whether the new triggers are now visible in the configuration view.

  //properties([
    //buildDiscarder(logRotator(numToKeepStr: '5')),
    //pipelineTriggers([
      //  pollSCM('H/5 * * * *')
    //])
  //])
  
 options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        //timeout(time: 10, unit: 'MINUTES')
        //timestamps()  // Requires the "Timestamper Plugin"
    }
    
    triggers {
        pollSCM('* * * * *')
    }
  
  stages {
    stage('SCM') {
        steps {
         //define scm connection for polling  
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
            //sh 'mvn clean package  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml sonar:sonar'
            sh 'mvn clean package   sonar:sonar'
        }
      }
    }
    
    stage("Quality Gate"){
        steps {
           timeout(time: 2, unit: 'MINUTES') {
            script {
               echo "-=- Get Sonarqube Quality Gate -=-"
              def qg = waitForQualityGate()
                 if (qg.status != 'OK' || qg.status == 'WARN') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                  }
             }
             
            //waitForQualityGate abortPipeline: true
           }
        }
    }
    
    
    //stage('Package') {
    //    steps {
    //        echo "-=- packaging project -=-"
    //        sh "./mvnw package -DskipTests"
    //        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
    //    }
    //}
    //
    //stage('Build Docker image') {
    //    steps {
    //        echo "-=- build Docker image -=-"
    //        sh "./mvnw docker:build"
    //    }
    //}
    
    
  }
}