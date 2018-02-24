@echo off
cd orbit-configurations
docker stop orbit-configurations
docker rm orbit-configurations
docker rmi orbit/configurations
call mvn clean install
docker run -d --name orbit-configurations -p 0.0.0.0:6999:6999 orbit/configurations
docker network connect app_net orbit-configurations
cd ..

cd orbit-discovery
docker stop orbit-discovery
docker rm orbit-discovery
docker rmi orbit/discovery
call mvn clean install
docker run -d --name orbit-discovery -p 0.0.0.0:7000:7000 --link orbit-configurations orbit/discovery
docker network connect app_net orbit-discovery

cd ..

pause

@echo on