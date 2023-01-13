# Library-Management-System
## List of supported APIs:
### Category Controller
| Methods  |         Urls         |          Actions           |
|:--------:|:--------------------:|:--------------------------:|
|   GET    |   /api/categories    |  retrieve all Categories   |
|   GET    | /api/categories/:id  | retrieve a Category by :id |
|   POST   |   /api/categories    |    create new Category     |
|   PUT    | /api/categories/:id  |  update a Category by :id  |
|  DELETE  |   /api/categories    |   delete all Categories    |
|  DELETE  | /api/categories/:id  |  delete a Category by :id  |
---

### Book Controller
| Methods | Urls                       | Actions                            |
|:--------|:---------------------------|:-----------------------------------|
| GET     | /api/books                 | retrieve all Books                 |
| GET     | /api/books/:id             | retrieve a Book by :id             |
| GET     | /api/books/{id}/authors    | retrieve all Authors of a Book     |
| GET     | /api/authors/{id}/books    | retrieve all Books of a Author     |
| GET     | /api/categories/:id/books  | retrieve all Books of a Category   |
| POST    | /api/authors/:id/books     | create/add Book for a Author       |
| POST    | /api/categories/:id/books  | create new Book for a Category     |
| PUT     | /api/books/:id             | update a Book by :id               |
| DELETE  | /api/books	                | delete all Books                   |
| DELETE  | /api/books/:id             | delete a Book by :id               |
| DELETE  | /api/categories/:id/books  | delete all Books of a Category     |
| DELETE  | /api/authors/:id/books/:id | delete a Book from a Author by :id |
 ---
### Author Controller
| Methods | Urls                | Actions                        |
|:--------|:--------------------|:-------------------------------|
| GET     | /api/authors        | retrieve all Authors           |
| GET     | /api/authors/:id    | retrieve a Author with it Tags |
| POST    | /api/authors        | create new Author              |
| PUT     | /api/authors/:id    | update a Author by :id         |
| DELETE  | /api/authors        | delete all Authors             |
| DELETE  | /api/authors/:id    | delete a Author by :id         |