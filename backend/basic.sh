#!/usr/bin/env bash
cd orbit-configurations
docker stop orbit-configurations
docker rm orbit-configurations
docker rmi orbit/configurations
mvn clean install
docker run -d --name orbit-configurations orbit/configurations
docker network connect app_net orbit-configurations
cd ..

cd orbit-discovery
docker stop orbit-discovery
docker rm orbit-discovery
docker rmi orbit/discovery
mvn clean install
docker run -d --name orbit-discovery -p 0.0.0.0:7000:7000 --link orbit-configurations orbit/discovery
docker network connect app_net orbit-discovery

cd ..

echo 'Basic services startup successfully'

