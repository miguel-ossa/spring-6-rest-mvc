DROP TABLE IF EXISTS beer_audit cascade;


CREATE TABLE beer_audit (
  audit_id VARCHAR(36) NOT NULL PRIMARY KEY,
  id VARCHAR(36) NOT NULL,
  version INTEGER,
  beer_name VARCHAR(50) NOT NULL,
  beer_style tinyint NOT NULL CHECK (beer_style BETWEEN 0 AND 9),
  upc VARCHAR(255) NOT NULL,
  quantity_on_hand INTEGER,
  price NUMERIC(38, 2) NOT NULL,
  created_date TIMESTAMP(6),
  update_date TIMESTAMP(6),

  created_date_audit TIMESTAMP(6),
  principal_name VARCHAR(50),
  audit_event_type VARCHAR(30)
) ENGINE = InnoDB;