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
	audience_id serial NOT NULL,
	audience_number character varying (50) NOT NULL,
	building_id int REFERENCES buildings (building_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	audience_capacity int NOT NULL,
	audience_description character varying (50),
	UNIQUE (audience_number, building_id),
	CONSTRAINT audience_pkey PRIMARY KEY (audience_id)
);
