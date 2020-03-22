###一、查询
```
POST /person/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "first_name": "he"
          }
        },
        {
          "match": {
            "last_name": "Wilder"
          }
        }
      ]
    }
  }
}
```
其中：
query:表示该语句是完成查询操作
must:表示数组内部的条件均需要满足，相当于sql中的"and"
should:表示数组内部的查询条件只要满足一条，相当于sql的"or"
