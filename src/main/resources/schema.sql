DROP TABLE IF EXISTS holidays CASCADE;
DROP TABLE IF EXISTS buildings CASCADE;
DROP TABLE IF EXISTS audiences CASCADE;
DROP TABLE IF EXISTS deans_office CASCADE;
DROP TABLE IF EXISTS university_office CASCADE;
DROP TABLE IF EXISTS lessons_timeschedule CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS faculties CASCADE;
DROP TABLE IF EXISTS specialities CASCADE;
DROP TABLE IF EXISTS years CASCADE;

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

CREATE TABLE university_office (
	university_office_id serial NOT NULL,
	university_office_name character varying (50) NOT NULL,
	university_office_address character varying (100) NOT NULL,
	CONSTRAINT university_office__pkey PRIMARY KEY (university_office_id)
);

CREATE TABLE lessons_timeschedule (
	lessons_timeschedule_id serial NOT NULL,
	lessons_timeschedule_name character varying (50) NOT NULL,
	CONSTRAINT lessons_timeschedule__pkey PRIMARY KEY (lessons_timeschedule_id)
);

CREATE TABLE deans_office (
	deans_office_id serial NOT NULL,
	deans_office_name character varying (50) NOT NULL,
	university_office_id int REFERENCES university_office (university_office_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	lessons_timeschedule_id int REFERENCES lessons_timeschedule (lessons_timeschedule_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	CONSTRAINT deans_office__pkey PRIMARY KEY (deans_office_id)
);

CREATE TABLE departments (
	department_id serial NOT NULL,
	department_name character varying (50) NOT NULL,
	CONSTRAINT departments__pkey PRIMARY KEY (department_id)
);

CREATE TABLE faculties (
	faculty_id serial NOT NULL,
	faculty_name character varying (50) NOT NULL,
	deance_office_id int REFERENCES deans_office (deans_office_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	CONSTRAINT faculties__pkey PRIMARY KEY (faculty_id)
);


CREATE TABLE specialities (
	speciality_id serial NOT NULL,
	speciality_name character varying (50) NOT NULL,
	faculty_id int REFERENCES faculties (faculty_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	CONSTRAINT specialities__pkey PRIMARY KEY (speciality_id)
);


CREATE TABLE years (
	year_id serial NOT NULL,
	year_name character varying (50) NOT NULL,
	speciality_id int REFERENCES specialities (speciality_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	CONSTRAINT years__pkey PRIMARY KEY (year_id)
);



