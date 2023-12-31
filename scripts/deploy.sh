#!/bin/bash

REPOSITORY=/home/ubuntu/goodspia-source
cd $REPOSITORY

APP_NAME=goodspia
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
DEPLOY_LOG_PATH="/home/ubuntu/$APP_NAME/deploy.log"
DEPLOY_ERR_LOG_PATH="/home/ubuntu/$APP_NAME/deploy_err.log"
APPLICATION_LOG_PATH="/home/ubuntu/$APP_NAME/application.log"


# =====================================
# 현재 구동 중인 application pid 확인
# =====================================
CURRENT_PID=$(pgrep -f java | head -1)
echo "> 현재 동작중인 어플리케이션 pid 체크" >> $DEPLOY_LOG_PATH

if [ -z "$CURRENT_PID" ]; then
    echo "NOT RUNNING" >> $DEPLOY_LOG_PATH
else
    echo "> kill -15 $CURRENT_PID" >> $DEPLOY_LOG_PATH
    kill -15 "$CURRENT_PID"
    sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar -Dspring.profiles.active=prod "$JAR_PATH" 1>>"$APPLICATION_LOG_PATH" 2>"$DEPLOY_ERR_LOG_PATH" &

sleep 2
echo "> 배포 종료 : $(date +%c)" >> $DEPLOY_LOG_PATH