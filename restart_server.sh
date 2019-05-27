#!/bin/sh
file="/home/ubuntu/vertx-demo/app_pid.pid"
if [ -f "$file" ]
then
    kill -9 `cat /home/ubuntu/vertx-demo/app_pid.pid`
fi

nohup java -jar /home/ubuntu/vertx-demo/vertx-demo-fat.jar &
echo "$!" > $file
