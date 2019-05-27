#!/bin/bash

key='../../keys/vertx-demo-aws.pem'
svr='ubuntu@18.221.177.61'
target_dir='/home/ubuntu/vertx-demo'

./gradlew build
chmod 400 $key
ssh -i $key -f $svr "mkdir -p $target_dir"
scp -i $key build/libs/vertx-demo-fat.jar $svr:$target_dir
scp -i $key restart_server.sh $svr:$target_dir
ssh -i $key -f $svr "sh $target_dir/restart_server.sh"
