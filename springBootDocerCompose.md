# Using Spring boot with docker compose

## Setup Docker 17

```bash
## Download deb
wget https://download.docker.com/linux/ubuntu/dists/xenial/pool/stable/amd64/docker-ce_17.03.2~ce-0~ubuntu-xenial_amd64.deb

## Install
sudo dpkg -i /path/to/package.deb
```

## Install Docker compose
```bash
sudo apt-get -y install python-pip

sudo pip install docker-compose
```

## An example of docker compose yaml file

`docker-compose-local.yaml`

```
version: '3.0'

services:

    ftp-server:
        image: 192.168.200.167:5000/cwsl_ftp_server
        depends_on:
          - db
        environment:
          IN_DOCKER: 1
          SPRING_PROFILE: container
          WAIT_FOR_HOST_AND_PORT: db:3306
        volumes:
          - ./app/files:/app/files
          - ./app/logs:/app/logs
        ports:
          - 6688:6688
          - 33933:33933  ## passive mode port

    file-processor:
        image: 192.168.200.167:5000/cwsl_file_processor
        depends_on:
          - ftp-server
        environment:
          IN_DOCKER: 1
          SPRING_PROFILE: loccontainer
          WAIT_FOR_HOST_AND_PORT: ftp-server:6688
        volumes:
          - ./app/files:/app/files
          - ./app/logs:/app/logs

    db:
        image: mysql
        environment:
            MYSQL_ROOT_PASSWORD: capitalflow!
            MYSQL_DATABASE: test
        ports:
            - 3306:3306
```

### Run containers

```bash
sudo docker-compose -f docker-compose-local.yaml up -d
```

### Tear down 
```bash
sudo docker-compose -f docker-compose-local.yaml down
```

## Configure in `application.properties`

#### mysql
```
spring.datasource.url=jdbc:mysql://localhost:3306/test?useSSL=false

spring.datasource.username=root

spring.datasource.password=capitalflow!

spring.jpa.hibernate.ddl-auto=create
```

#### logs

```
spring.main.banner-mode=log
logging.file: app/logs/cwsl-file-processor.log

logging.level.root=INFO
logging.level.ie.ait.sri.cwsl.product.CwslFileProcessor=DEBUG
logging.level.ie.ait.sri.cwsl.product.FileProcessor=DEBUG
```

## Configure in `application-loccontainer.properties`

**NOTE :** the String `loccontainer` will be used as environment varialbe to specify the properties file to use. see <a href="ftpserverApp/start.sh">start.sh</a>

```
spring.datasource.url=jdbc:mysql://db:3306/test?useSSL=false
```

**NOTE :** only this line will be replaced, the rest will be remained.