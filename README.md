
# E-Commerce REST API

A REST API for an ecommerce clothing store.


## API Reference

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
## Documentation




## Lessons Learned

-


## Acknowledgements

-

 

