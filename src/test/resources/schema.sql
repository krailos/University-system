DROP TABLE IF EXISTS holidays CASCADE;
DROP TABLE IF EXISTS audiences CASCADE;
DROP TABLE IF EXISTS years CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS lesson_times CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS subjects CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
DROP TABLE IF EXISTS timetables CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
DROP TABLE IF EXISTS lessons CASCADE;
DROP TABLE IF EXISTS vocations CASCADE;
DROP TABLE IF EXISTS lessons_groups CASCADE;
DROP TABLE IF EXISTS teachers_subjects CASCADE;
DROP TABLE IF EXISTS years_subjects CASCADE;

DROP TYPE IF EXISTS gender CASCADE;
DROP TYPE IF EXISTS kind CASCADE;
CREATE TYPE gender AS enum ('MALE', 'FEMALE');
CREATE TYPE kind AS enum ('GENERAL', 'PREFERENTIAL');


CREATE TABLE holidays (
	id serial NOT NULL,
	name character varying (50) NOT NULL,
	date date NOT NULL,
	CONSTRAINT holidays__pkey PRIMARY KEY (id)
);


CREATE TABLE audiences (
	id serial NOT NULL,
	number character varying (50) NOT NULL,
	capacity int NOT NULL,
	description character varying (50),
	UNIQUE (number),
	CONSTRAINT audience_pkey PRIMARY KEY (id)
);

CREATE TABLE years (
	id serial NOT NULL,
	name character varying (50) NOT NULL,
	CONSTRAINT years__pkey PRIMARY KEY (id),
	UNIQUE (name)
);

CREATE TABLE groups (
	id serial NOT NULL,
	name character varying (50) NOT NULL,
	year_id int REFERENCES years (id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT groups__pkey PRIMARY KEY (id)
);

CREATE TABLE lesson_times (
	id serial NOT NULL,
	order_number character varying (50) NOT NULL,
	start_time time NOT NULL,
	end_time time NOT NULL,
	CONSTRAINT lesson_times__pkey PRIMARY KEY (id)
);

CREATE TABLE students (
	id serial NOT NULL,
	student_id character varying (50),
	first_name character varying (50) NOT NULL,
	last_name character varying (50) NOT NULL,
	birth_date date NOT NULL,
	phone character varying (50) NOT NULL,
	address character varying (50) NOT NULL,
	email character varying (50) NOT NULL,
	rank character varying (50) NOT NULL,	
	gender gender,
	group_id int REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT students__pkey PRIMARY KEY (id)
);

CREATE TABLE subjects (
	id serial NOT NULL,
	name character varying (50) NOT NULL,
	description character varying (50),
	CONSTRAINT subjects__pkey PRIMARY KEY (id)
);

CREATE TABLE teachers (
	id serial NOT NULL,
	teacher_id character varying (50),
	first_name character varying (50) NOT NULL,
	last_name character varying (50) NOT NULL,
	birth_date date NOT NULL,
	phone character varying (50) NOT NULL,
	address character varying (50) NOT NULL,
	email character varying (50) NOT NULL,
	degree character varying (50) NOT NULL,	
	gender gender,
	CONSTRAINT teachets__pkey PRIMARY KEY (id),
	UNIQUE(teacher_id)
);

CREATE TABLE timetables (
	id serial NOT NULL,
	name character varying (50) NOT NULL,
	CONSTRAINT timetables__pkey PRIMARY KEY (id),
	UNIQUE (name)
);

CREATE TABLE lessons (
	id serial NOT NULL,
	date date NOT NULL,
	lesson_time_id int REFERENCES lesson_times (id) ON UPDATE CASCADE ON DELETE CASCADE,
	subject_id int REFERENCES subjects (id) ON UPDATE CASCADE ON DELETE CASCADE,
	teacher_id int REFERENCES teachers (id) ON UPDATE CASCADE ON DELETE CASCADE,
	audience_id int REFERENCES audiences (id) ON UPDATE CASCADE ON DELETE CASCADE,	
	CONSTRAINT lessons__pkey PRIMARY KEY (id)
);

CREATE TABLE vocations (
	id serial NOT NULL,
	kind kind,
	applying_date date NOT NULL,
	start_date date NOT NULL,
	end_date date NOT NULL,
	teacher_id int REFERENCES teachers (id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT teachers__pkey PRIMARY KEY (id)
);

CREATE TABLE lessons_groups (
	lesson_id int REFERENCES lessons (id) ON UPDATE CASCADE ON DELETE CASCADE,
	group_id int REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE (lesson_id, group_id)
); 

CREATE TABLE teachers_subjects (
	teacher_id int REFERENCES teachers (id) ON UPDATE CASCADE ON DELETE CASCADE,
	subject_id int REFERENCES subjects (id) ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE (teacher_id, subject_id)
);

CREATE TABLE years_subjects (
	year_id int REFERENCES years (id) ON UPDATE CASCADE ON DELETE CASCADE,
	subject_id int REFERENCES subjects (id) ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE (year_id, subject_id)
);


