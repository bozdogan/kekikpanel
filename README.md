# KekikPanel

Çok kullanıcılı "not alma" uygulaması.

Kullanılan teknolojiler:
- Spring Web
- Thymeleaf
- H2

Test verileri:

| Kullanıcı Adı | Parola  | Yönetici mi? |
|---------------|---------|--------------|
| admin         | `admin` | Evet         |
| cansimit      | `1234`  | Hayır        |


## API Endpoints

### `/api/users`

- GET /api/users
- GET /api/users/{username}
- POST /api/users
- PUT /api/users/{username}
- DELETE /api/users/{username}


### `/api/notes`

- GET /api/notes
- GET /api/notes/{id}
- GET /api/notes/of/{username}
- POST /api/notes
- PUT /api/notes/{id}
- DELETE /api/notes/{id}

<!--
    Bora Özdoğan
    Mayıs 2022
-->
