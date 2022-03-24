# mypetstore-api

## front

### 1.catalog

#### 1.1 查询所有Category信息

method: GET

requestUrl:`:/category/categories`

response:

```json
{
  "status": 0,
  "data": [
    {
      "categoryId": "BIRDS",
      "name": "Birds",
      "description": "<image src=\"../images/birds_icon.gif\"><font size=\"5\" color=\"blue\"> Birds</font>"
    },
    {
      "categoryId": "CATS",
      "name": "Cats",
      "description": "<image src=\"../images/cats_icon.gif\"><font size=\"5\" color=\"blue\"> Cats</font>"
    },
    {
      "categoryId": "DOGS",
      "name": "Dogs",
      "description": "<image src=\"../images/dogs_icon.gif\"><font size=\"5\" color=\"blue\"> Dogs</font>"
    },
    {
      "categoryId": "FISH",
      "name": "Fish",
      "description": "<image src=\"../images/fish_icon.gif\"><font size=\"5\" color=\"blue\"> Fish</font>"
    },
    {
      "categoryId": "REPTILES",
      "name": "Reptiles",
      "description": "<image src=\"../images/reptiles_icon.gif\"><font size=\"5\" color=\"blue\"> Reptiles</font>"
    }
  ]
}
```

#### 1.2 由id查询某个Category的信息

method: GET

requestUrl: `/catalog/categories/{id}`

example: `/catalog/categories/DOGS`

response: 
```json
{
    "status": 0,
    "data": {
        "categoryId": "DOGS",
        "name": "Dogs",
        "description": "<image src=\"../images/dogs_icon.gif\"><font size=\"5\" color=\"blue\"> Dogs</font>"
    }
}
```

#### 1.3 查询某个Category下的所有Product信息

method: GET

requestUrl: `/catalog/categories/{id}/product`

example: `/catalog/categories/DOGS/product`

response:
```json
{
    "status": 0,
    "data": [
        {
            "productId": "K9-BD-01",
            "categoryId": "DOGS",
            "name": "Bulldog",
            "description": "<image src=\"/images/dog2.gif\">Friendly dog from England"
        },
        {
            "productId": "K9-CW-01",
            "categoryId": "DOGS",
            "name": "Chihuahua",
            "description": "<image src=\"/images/dog4.gif\">Great companion dog"
        },
        {
            "productId": "K9-DL-01",
            "categoryId": "DOGS",
            "name": "Dalmation",
            "description": "<image src=\"/images/dog5.gif\">Great dog for a Fire Station"
        },
        {
            "productId": "K9-PO-02",
            "categoryId": "DOGS",
            "name": "Poodle",
            "description": "<image src=\"/images/dog6.gif\">Cute dog from France"
        },
        {
            "productId": "K9-RT-01",
            "categoryId": "DOGS",
            "name": "Golden Retriever",
            "description": "<image src=\"/images/dog1.gif\">Great family dog"
        },
        {
            "productId": "K9-RT-02",
            "categoryId": "DOGS",
            "name": "Labrador Retriever",
            "description": "<image src=\"/images/dog5.gif\">Great hunting dog"
        }
    ]
}
```

#### 1.4 由productId获取product

method: GET

requestUrl: `/catalog/products/{id}`

example: `/catalog/products/K9-RT-01`

response: 
```json
{
    "status": 0,
    "data": {
        "productId": "K9-RT-01",
        "categoryId": "DOGS",
        "name": "Golden Retriever",
        "description": "<image src=\"/images/dog1.gif\">Great family dog"
    }
}
```

#### 1.5 查询productId对应的Product下的所有Item

method: GET

requestUrl: `/catalog/products/{id}/items`

example: `/catalog/products/K9-RT-01/items`

response:

```json
{
    "status": 0,
    "data": [
        {
            "itemId": "EST-28",
            "productId": "K9-RT-01",
            "listPrice": 155.29,
            "unitCost": 90.00,
            "supplierId": 1,
            "status": "P",
            "attribute1": "Adult Female",
            "attribute2": null,
            "attribute3": null,
            "attribute4": null,
            "attribute5": null,
            "product": {
                "productId": "K9-RT-01",
                "categoryId": "DOGS",
                "name": "Golden Retriever",
                "description": "<image src=\"/images/dog1.gif\">Great family dog"
            },
            "quantity": 10000
        }
    ]
}
```

#### 1.6