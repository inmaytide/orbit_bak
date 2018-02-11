cd orbit-configurations
docker stop orbit-configurations
docker rm orbit-configurations
docker rmi orbit/configurations
call mvn clean install
docker run -d --name orbit-configurations orbit/configurations

cd ..

cd orbit-discovery
docker stop orbit-discovery
docker rm orbit-discovery
docker rmi orbit/discovery
call mvn clean install
docker run -d --name orbit-discovery -p 0.0.0.0:7000:7000 --link orbit-configurations orbit/discovery

cd ..

pause

exit