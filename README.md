# booking
Simple app to book a place to stay

# To run the application:
    - download repository to your local machine;
    - run docker;
    - open your favorite terminal and got to the repository path(where DockerFile is);
    - create docker image by running command : ” docker build -t booking:v1 .”
    - run docker container by command "docker run --name booking --rm -p 8080:8080 booking:v1 "

# Your instance of application is up and running

    Go to http://localhost:8080/swagger-ui/index.html# and try it out using swagger endpoints