
# Todo

Todo backend example for [Todo Backend](http://www.todobackend.com/index.html) using Hexagon. Built 
with [Travis CI](https://travis-ci.org) and hosted at Heroku here: 
[https://todo-backend-hexagonkt.herokuapp.com/](https://todo-backend-hexagonkt.herokuapp.com/)

The spec is:

GET `/tasks/{id}`

Response:
```json
{
    "url": "/tasks/{id}",
    "title": string
    "order": int?,
    "completed": boolean
}
```

GET `/tasks`

Response:
```json
[
    {
        "url": "/tasks/{id}",
        "title": string
        "order": int?,
        "completed": boolean
    },
    ...
}
```

POST `/tasks`

Request:
```json
{
  "title": string,
  "order": int?
}
```

Response:
```json
{
    "url": "/tasks/{id}",
    "title": string
    "order": int?,
    "completed": boolean
}
```

PATCH `/tasks/{id}`
Request:
```json
{
  "title": string?,
  "order": int?
  "completed": boolean?
}
```

Response:
```json
{
    "url": "/tasks/{id}",
    "title": string
    "order": int?,
    "completed": boolean
}
```
DELETE `/tasks/{id}`
Response: 200 OK

DELETE `/tasks`
Response: 200 OK



### Build
Import the gradle project to build the code.

### Deploy

Set the environment variable `SERVICE_serviceURL` to whatever your host is 
to generate the correct url for tasks.

Set `SERVICE_mongoDbUrl` to the url for your MongoDb database.
