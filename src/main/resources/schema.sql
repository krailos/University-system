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

	
INSERT INTO holidays (holiday_name, holiday_date) VALUES 
('New Year', '01.01.2021'),
('Independant Day', '24.08.2021');

INSERT INTO buildings (building_name, building_address) VALUES 
('Main building', 'pr. Peremohy, 25'),
('Faculty of Finance building', 'st. I.Lepse, 12');

INSERT INTO audiences (audience_number, building_id, audience_capacity, audience_description) VALUES
('1', '1','300', 'conference hall'),
('1', '2','120', 'common audiene'),
('2', '2','120', 'common audiene'),
('3', '2','120', 'common audiene');
