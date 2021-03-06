
# E-Commerce REST API

A REST API for a ecommerce clothing store.


## API Documentation

[Postman documentation for API endpoints and example responses.](https://documenter.getpostman.com/view/19652837/UVsFz8tX)


## API Reference

#### PRODUCTS
#### Get all products

```http
  GET /api/v1/collection/all
```

#### Get product by ID

```http
  GET /api/v1/products/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. ID of product to fetch |

#### Get product by name

```http
  GET /api/v1/allproducts/${name}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | **Required**. name of product to fetch |

#### Create product

```http
  POST /api/v1/addproduct
```

#### Update product by ID

```http
  PUT /api/v1/products/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. ID of product to update |

#### Delete product by ID

```http
  DELETE /api/v1/products/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. ID of product to delete |

#### CATEGORY

#### Get all categories

```http
  GET /api/v1/categories
```

#### Get category by name

```http
  GET /api/v1/category/${name}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. name of category to fetch |

#### Create category

```http
  POST /api/v1/addcategory
```

#### Update category by ID

```http
  PUT /api/v1/category/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. ID of category to update |

#### Delete category by ID

```http
  DELETE /api/v1/category/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. ID of category to delete |

#### USERS

#### Get all users

```http
  GET /api/v1/allusers
```

#### Get user by ID

```http
  GET /api/v1/user/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. id of user to fetch |

#### Sign-up user

```http
  POST /api/v1/signup
```

#### Log-in user

```http
  POST /api/v1/login
```

#### Update user by ID

```http
  PUT /api/v1/user/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. ID of user to update |

#### Delete user by ID

```http
  DELETE /api/v1/user/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. ID of user to delete |

#### CART

#### Get all cart items

```http
  GET /api/v1/cart
```

#### Add to cart

```http
  POST /api/v1/addtocart
```

#### Update cart item by ID

```http
  PUT /api/v1/updatecart/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. ID of cart item to update |

#### Delete cart item by ID

```http
  DELETE /api/v1/deleteitem/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. ID of cart item to delete |

## Lessons Learned

- CRUD cyle
- MVC(S) Architectural design pattern
- Data Transfer Objects and their uses
- Database management (PostgreSQL)
- Spring Security
- JWT - JSON Web Tokens

