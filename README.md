# PasswordStorageService

In order to setup this service, you'll need:
1. An AWS account
2. A MongoDB instance
3. An `application.properties` file under `src/main/resources`.

Your AWS account should include:

1. An IAM user with access Key and secret exposed.</li>
2. A DynamoDB table named `password-record`, primary key `recordId`, GSI `userId` and index name `userId-index`.
3. A DynamoDB table named `user-record`, primay key `userId`.

Your `application.properties` file should look like this:
```properties
cloud.aws.credentials.accessKey=<accessKey>
cloud.aws.credentials.secretKey=<secretKey>
cloud.aws.credentials.instanceProfile=true
cloud.aws.credentials.useDefaultAwsCredentialsChain=true
cloud.aws.region.static=<region>
cloud.aws.stack.auto=false

spring.session.store-type=mongodb
spring.data.mongodb.uri=mongodb://<username>:<password>@<host>:<port>/<database-name>?retryWrites=false
```