DROP TABLE IF EXISTS beer cascade;

DROP TABLE IF EXISTS customer cascade;

CREATE TABLE beer (
  beer_style tinyint NOT NULL CHECK (beer_style BETWEEN 0 AND 9),
  price NUMERIC(38, 2) NOT NULL,
  quantity_on_hand INTEGER,
  version INTEGER,
  created_date TIMESTAMP(6),
  update_date TIMESTAMP(6),
  id VARCHAR(36) NOT NULL,
  beer_name VARCHAR(50) NOT NULL,
  upc VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE customer (
  version INTEGER,
  created_date TIMESTAMP(6),
  last_modified_date TIMESTAMP(6),
  id VARCHAR(36) NOT NULL,
  customer_name VARCHAR(255),
  PRIMARY KEY (id)
);
