docker build . -t ms-conveyor --build-arg appName=ms-conveyor.jar
docker run --name ms-conveyor -p 8081:8081 ms-conveyor