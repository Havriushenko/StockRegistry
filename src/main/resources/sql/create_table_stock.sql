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