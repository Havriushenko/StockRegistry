CREATE TABLE stock(
  pk INT8 NOT NULL,
  comment VARCHAR(2000),
  usreou INT8 NOT NULL,
  quantity INT8 NOT NULL,
  total_nominal_value NUMERIC(19,2) NOT NULL,
  nominal_value NUMERIC(19,2) NOT NULL,
  release_date timestamp with time zone NOT NULL,
  status VARCHAR(10),
primary key (pk)
);



              "dbms":  "!h2, postgres",
"schemaName":  "public",




,
{
"insert": {
"columns": [
{
"column": {
"name": "comment",
"value": "Hello World!"
},
"column": {
"name": "usreou",
"value": 687796
},
"column": {
"name": "quantity",
"value": 5
},
"column": {
"name": "total_nominal_value",
"value": 502.75
},
"column": {
"name": "nominal_value",
"value": 100.55
},
"column": {
"name": "release_date",
"value": "2004-10-19T11:45:54+03:00"
},
"column": {
"name": "status",
"value": "active"
}
}
],
"tableName": "stock"
}
},
{
"insert": {
"columns": [
{
"column": {
"name": "comment",
"value": "Hello World!"
},
"column": {
"name": "usreou",
"value": 687796
},
"column": {
"name": "quantity",
"value": 5
},
"column": {
"name": "total_nominal_value",
"value": 502.75
},
"column": {
"name": "nominal_value",
"value": 100.55
},
"column": {
"name": "release_date",
"value": "2004-10-19T11:35:54+03:00"
},
"column": {
"name": "status",
"value": "deleted"
}
}
],
"tableName": "stock"
}
}