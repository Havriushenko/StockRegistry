CREATE TABLE history(
  pk INT8 NOT NULL,
  usreou INT8 NOT NULL,
  field_name VARCHAR (500) NOT NULL,
  last_change VARCHAR (2000) NOT NULL,
  new_change VARCHAR (2000) NOT NULL,
  date_of_modified timestamp with time zone NOT NULL,
primary key (pk)
);