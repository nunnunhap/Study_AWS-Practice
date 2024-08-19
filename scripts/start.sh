#!/usr/bin/env bash
# start.sh

PROJECT="study-1.0.0"
PROJECT_NAME=study
PROJECT_FULL_PATH="/home/ec2-user/app3/step2"
JAR_FILE="$PROJECT_FULL_PATH/$PROJECT.jar"
LOG_PATH="$PROJECT_FULL_PATH/logs"

APP_LOG="$LOG_PATH/application.log"
ERROR_LOG="$LOG_PATH/error.log"
DEPLOY_LOG="$LOG_PATH/0_deploy.log"

NOW_DATETIME=$(date "+%Y-%m-%d-%aT%T")

if [ ! -d $LOG_PATH ]; then
    mkdir $LOG_PATH
fi

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG

cd $PROJECT_FULL_PATH

cp $PROJECT_FULL_PATH/study/target/*.jar $JAR_FILE
#sudo cp /home/ec2-user/app/step2/study/target/study-1.0.0.jar.jar study-1.0.0.jar

cd $PROJECT_FULL_PATH

# jar 파일실행
#nohup java -jar $JAR_FILE 1>$APP_LOG 2>$ERROR_LOG &
#nohup java -jar $JAR_FILE 1>>$APP_LOG 2>>$ERROR_LOG &
#nohup java -jar -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app3/application-prod-db.properties -Dspring.profiles.active=prod $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

nohup java -jar -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app3/application-real-db.properties $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

sleep 30s

CURRENT_PID=$(pgrep -f $JAR_FILE)

if [ -z $CURRENT_PID ]; then
  echo "$NOW_DATETIME :: $JAR_FILE :: failed to start!" >> $DEPLOY_LOG
else
  echo "$NOW_DATETIME :: $JAR_FILE :: $CURRENT_PID started!" >> $DEPLOY_LOG
fi
