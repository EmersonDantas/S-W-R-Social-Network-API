CREATE TABLE rebel (
	id int8 NOT NULL,
	base varchar(255) NOT NULL,
	date_of_birth timestamp NOT NULL,
	denunciations int4 NOT NULL,
	galaxy varchar(255) NOT NULL,
	genre int4 NOT NULL,
	is_renegade bool NOT NULL,
	latitude float8 NULL,
	location_name varchar(255) NULL,
	longitude float8 NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT rebel_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE rebel_sequence INCREMENT BY 1;
