DROP TABLE IF EXISTS holidays CASCADE;
DROP TABLE IF EXISTS buildings CASCADE;
DROP TABLE IF EXISTS audiences CASCADE;

CREATE TABLE holidays (
	holiday_id serial NOT NULL,
	holiday_name character varying (50) NOT NULL,
	holiday_date date NOT NULL,
	CONSTRAINT holidays__pkey PRIMARY KEY (holiday_id)
);

CREATE TABLE buildings (
	building_id serial NOT NULL,
	building_name character varying (50) NOT NULL,
	building_address character varying (100) NOT NULL,
	CONSTRAINT building__pkey PRIMARY KEY (building_id)
);

CREATE TABLE audiences (
	id serial NOT NULL,
	number character varying (50) NOT NULL,
	building_id int  NOT NULL,
	capacity int NOT NULL,
	description character varying (50),
	UNIQUE (number, building_id)
);
