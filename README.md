
# Todo
Todo backend example for [Todo Backend](http://www.todobackend.com/index.html) using Hexagon.

The spec is:

GET `/tasks/{id}`

Response:
```
{
    "url": "/tasks/{id}",
    "title": string,
    "order": int?,
    "completed": boolean
}
```

GET `/tasks`

Response:
```
[
    {
        "url": "/tasks/{id}",
        "title": string,
        "order": int?,
        "completed": boolean
    },
    ...
}
```

POST `/tasks`

Request:
```
{
  "title": string,
  "order": int?
}
```

Response:
```
{
    "url": "/tasks/{id}",
    "title": string
    "order": int?,
    "completed": boolean
}
```

PATCH `/tasks/{id}`

Request:
```
{
  "title": string?,
  "order": int?
  "completed": boolean?
}
```

Response:
```
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
```bash
./gradlew build jpackage
docker compose --profile local build
# Or
REGISTRY="k3d.localhost:5000/" docker compose --profile local build
```

### Deploy
Set the environment variable `SERVICE_URL` to whatever your host is
to generate the correct url for tasks.

Set `MONGODB_URL` to the url for your MongoDb database.
