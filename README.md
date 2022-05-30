# KekikPanel

Çok kullanıcılı "not alma" uygulaması.

Admin paneli tamamen client-side çalışıp AJAX aracılığıyla API çağrısı yapmaktadır.  Kullanıcı paneli server-side render olup geriplanda doğrudan ya da HTTP üzerinden (konfigüre edilebilir şekilde) API çağrısı yapmaktadır.


## Kullanılan Teknolojiler
- Spring Web
- Thymeleaf
- H2


## Test Kullanıcıları
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
