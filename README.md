![PuppetAPI](./images/icon.png)

## Development

### Setup
- Install JDK11
- Install postgresql
- Create ``mock`` database
- Modify ``application.yml`` to set your PostgreSQL credentials.

#### Start the application
```shell
./gradlew run
```

#### Run test
```shell
./gradlew test
```

### Dockerize
```shell
docker-compose build
```

```shell
docker-compose up -d
```
Note: You have to create database in postgres container.

## Micronaut 2.5.3 Documentation

- [User Guide](https://docs.micronaut.io/2.5.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.5.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.5.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)