CREATE TABLE country (
	id INT,
	country_name VARCHAR,
	CONSTRAINT pk_country PRIMARY KEY (id)
);

CREATE TABLE person (
	id INT,
	name VARCHAR,
	birthday DATE,
	country_id INT,
	CONSTRAINT pk_person PRIMARY KEY (id),
	CONSTRAINT fk_person_country FOREIGN KEY (id) REFERENCES country(id)
);