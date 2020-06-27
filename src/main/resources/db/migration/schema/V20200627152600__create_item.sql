CREATE TABLE item (
	id int8 NOT NULL,
	amount int4 NOT NULL,
	"name" varchar(255) NULL,
	points int4 NOT NULL,
	rebel_id int8 NULL,
	CONSTRAINT item_pkey PRIMARY KEY (id),
	CONSTRAINT fke1ttieayyaxd0df9a7r0dlg6t FOREIGN KEY (rebel_id) REFERENCES rebel(id)
);

CREATE SEQUENCE item_sequence INCREMENT BY 1;
