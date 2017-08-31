# Using Spring boot with mysql

### Spring boot for mysql

1. run mysql in container
2. create a database in mysql such as 'test'
3. connect to database: 

### Configure in `application.properties`

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

