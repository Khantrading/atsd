# Retrieve Entity Tags

## Request

### URI

```elm
POST /api/v1/properties/query
```

### Payload

```json
[
  {
    "type": "$entity_tags",
    "entity": "nurswgvml007"
  }
]
```

## Response

```json
[
  {
    "type": "$entity_tags",
    "entity": "nurswgvml007",
    "key":
    {
    },
    "tags":
    {
        "alias": "007",
        "app": "ATSD",
        "ip": "192.0.2.6",
        "loc_area": "dc2",
        "loc_code": "nur,nur",
        "os": "Linux"
    },
    "date": "2018-09-08T09:06:32Z"
  }
]
```
