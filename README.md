# Information-portal

[[_TOC_]]
## Description
General information portal where questions can be answered by THM members and questions and answers can be rated.
To get a better picture, please visit these websites

Good question
Stack Overflow

Each of these websites, serves as a contact point for many to get answers to a specific question.
We would like to create a similar service for the THM, which will allow students and all other THM members to ask many questions that

## Feature
Create, Update and Edit:
- Questions, 
- Answers and 
- Comments
- ...

All features can be viewed on this page in the wiki
- [aktuelle Feautes](./../../wikis/Documentation/1-Features)


## Documentation
0.  [Introduction](./../../wikis/documentation/0.1-Introduction)
1. [Architecture](./../../wikis/documentation/0.2-Architecture)
2. [Features](./../../wikis/documentation/1-Features)
3. [Backend](./../../wikis/documentation/2-Backend)
4. [Frontend](./../../wikis/documentation/3-Frontend)
5. [Database](./../../wikis/documentation/4-Database)
7. [On Boarding](./../../wikis/6-On-Boarding)
8. [WIKI](./../../wikis/pages)


## Start
### Dependence
We need another repo for the start, which provides a gateway that we use to forward all requests to frontend and backend.
- All info about this [here]()

The gateway can be started with the following command in the directory:

```
$> gradle bootRun
```


### Frontend
To start the frontend it is necessary to install all the needed packages via ```npm```. Therefore we have to be in the frontend folder.

1. Check path with:
```
$> pwd
or
$> ls
```

2. Install all packages for this project.  This command is sufficient:

```
$> npm i
//or
$> npm install
```

Start the UI with:
```
$> npm run serve:local
```

### Backend
To start the backend you have to be in the directory backend.

Check path with:
```
$> pwd
or
$> ls
```

```
./mvn spring-boot:run
```


#### Unit test

If *maven* is installed globally:
```
$> mvn clean test
```


### Maven
Alternatively the tool can be used in the project:

- Linux:

    ```
    $> ./mvnw clean test
    ```

- Windows:
    ```
    $> ./mvnw.cmd clean test
    ```

### Gradle
1. If gradle is installed globally:
    ```
    $> gradle bootRun
    ```

2. Alternatively the tool can be used in the project:
    ```
    $> ./gradlew bootRun
    ```
### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
