pipeline {
    agent any

    parameters {
       choice(name: 'Action', choices: ['build','deploy'], description: 'build: to build an image and push to dockerhub. deploy: to deploy to k8s')
       choice(name: 'Region', choices: ['us-east-1'], description: 'AWS REGION')
       string(name: 'ReleaseVersion', defaultValue:'1', description: 'RELEASE VERSION NUMBER')
    }

    options {
        ansiColor('xterm')
        timestamps()
    }

    environment {
            DEF_AWS_ACCESS_KEY_ID = credentials('aws_access_key_id')
            DEF_AWS_ACCESS_SECRET_KEY = credentials('aws_access_key')
            AWS_REGION = "${params.Region}"
            RELEASE_VERSION = "${params.ReleaseVersion}"
            DOCKERHUBUSERNAME = credentials('dockerhub_username')
            DOCKERHUBPASSW = credentials('dockerhub_pw')
    }

    stages {
        
	    stage('Set version') {
            steps {
              script {
                // use 'version' to tag an image before publishing to a docker registry 
                def tokenizedVersion = "${RELEASE_VERSION}".tokenize(".")
                def release = tokenizedVersion[0]
                version = "${release}.${env.BUILD_NUMBER}.${env.GIT_COMMIT.substring(0, 5)}"  
                }
                echo "${version}"
              }
            }
        
        stage('Install aws cli and configure') {
            steps {
              sh """
              curl -s "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" > /dev/null
              unzip -qq awscliv2.zip
              sudo ./aws/install --update
              aws configure --region ${AWS_REGION} set aws_access_key_id ${DEF_AWS_ACCESS_KEY_ID} 
              aws configure --region ${AWS_REGION} set aws_secret_access_key ${DEF_AWS_ACCESS_SECRET_KEY}
              aws configure list
              """
            }
        }

        stage('Build artifact') {
            steps {
             sh "mvn clean install"  
            }
        }
        

        stage('Build Docker Image & Push to repository') {
            when {
                expression { "${params.Action}" == 'build' }
            }
            steps {
             script{
                print ('Building the image')
                sh """
                sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine -y
                sudo yum update -y --skip-broken
                sudo yum install -y docker
                sudo service docker start
                sudo usermod -a -G docker jenkins
                sudo docker system prune -a -f
                sudo docker build . --no-cache -t ${DOCKERHUBUSERNAME}/petstore3:${version}
                sudo docker login --username=${DOCKERHUBUSERNAME} --password ${DOCKERHUBPASSW}
                sudo docker push ${DOCKERHUBUSERNAME}/petstore3:${version}
                sudo docker logout
                """
              } 
            }
        }

        stage('Deploy to ECS') {
            when {
                expression { "${params.Action}" == 'deploy' }
            }
            steps {
              script{
                print ('deploying to ecs')
                def taskDef
                def container_definition
                def task_family
                def new_task_revision
                sh "aws ecs describe-task-definition --task-definition petstore3 --region ${AWS_REGION} --output json > describe_task_response.json"
                taskDef = readJSON file: 'describe_task_response.json'
                container_definition = taskDef['taskDefinition']['containerDefinitions']
                current_image = container_definition['image'].toString().replace("[", "").replace("]", "")
                task_family = taskDef['taskDefinition']['family']
                new_task_revision = taskDef['taskDefinition']['revision'].toInteger() + 1
                writeJSON(file: 'containerDefinitions.json', json: container_definition)
                sh """
                sed -i -e 's;${current_image};${DOCKERHUBUSERNAME}/petstore3:${version};g' containerDefinitions.json
                echo "NEW TASK DEFINITION(REVISON: ${new_task_revision}"
                cat containerDefinitions.json
                echo ""
                aws ecs register-task-definition \
                    --family ${task_family} \
                    --container-definitions file://containerDefinitions.json \
                    --region ${AWS_REGION}
                echo ""
                aws ecs update-service --service petstore3 --task-definition petstore3:${new_task_revision} --region ${AWS_REGION} --cluster main
                """
              } 
             
             }
        }
    }




	}
    post { 
        always {
          cleanWs()
        }
    }
}