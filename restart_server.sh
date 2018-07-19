#!/bin/sh
file="/home/ubuntu/vala-demo-vertx/app_pid.pid"
if [ -f "$file" ]
then
    kill -9 `cat /home/ubuntu/vala-demo-vertx/app_pid.pid`
fi

nohup java -jar /home/ubuntu/vala-demo-vertx/vala-demo-vertx-fat.jar &
echo "$!" > $file
