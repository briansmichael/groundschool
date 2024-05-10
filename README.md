# How To Run in Docker

* Install docker
* Put the sqlite databases in src/databases
* Copy `src/main/resources/application.yaml` into `./hidden_application.yaml` and update the variables
* `docker build -t groundschool .`
* If that completes, then run `docker run -d -p 8080:8080 docker.io/library/groundschool`