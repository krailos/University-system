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
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS lesson_times CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS subjects CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
DROP TABLE IF EXISTS timetables CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
DROP TABLE IF EXISTS vocations CASCADE;
DROP TABLE IF EXISTS lessons CASCADE;

DROP TYPE gender CASCADE;
CREATE TYPE gender AS enum ('male', 'female');

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

CREATE TABLE groups (
	group_id serial NOT NULL,
	group_name character varying (50) NOT NULL,
	year_id int REFERENCES years (year_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	speciality_id int REFERENCES specialities (speciality_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	CONSTRAINT groups__pkey PRIMARY KEY (group_id)
);

CREATE TABLE lesson_times (
	lesson_time_id serial NOT NULL,
	lesson_time_lesson character varying (50) NOT NULL,
	lesson_time_starttime time NOT NULL,
	lesson_time_endtime time NOT NULL,
	lessons_timeschedule_id int REFERENCES lessons_timeschedule (lessons_timeschedule_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	CONSTRAINT lesson_times__pkey PRIMARY KEY (lesson_time_id)
);

CREATE TABLE students (
	student_id serial NOT NULL,
	student_studentid character varying (50) NOT NULL,
	student_first_name character varying (50) NOT NULL,
	student_last_name character varying (50) NOT NULL,
	student_birthday date NOT NULL,
	student_phone character varying (50) NOT NULL,
	student_address character varying (50) NOT NULL,
	student_email character varying (50) NOT NULL,
	student_rank character varying (50) NOT NULL,	
	speciality_id int REFERENCES specialities (speciality_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	faculty_id int REFERENCES faculties (faculty_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	group_id int REFERENCES groups (group_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	student_gender gender,
	CONSTRAINT students__pkey PRIMARY KEY (student_id)
);

CREATE TABLE subjects (
	subject_id serial NOT NULL,
	subject_name character varying (50) NOT NULL,
	CONSTRAINT subjects__pkey PRIMARY KEY (subject_id)
);

CREATE TABLE teachers (
	teacher_id serial NOT NULL,
	teacher_teacherid character varying (50) NOT NULL,
	teacher_first_name character varying (50) NOT NULL,
	teacher_last_name character varying (50) NOT NULL,
	teacher_birthday date NOT NULL,
	teacher_phone character varying (50) NOT NULL,
	teacher_address character varying (50) NOT NULL,
	teacher_email character varying (50) NOT NULL,
	teacher_degree character varying (50) NOT NULL,	
	department_id int REFERENCES departments (department_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	teacher_gender gender,
	CONSTRAINT teachets__pkey PRIMARY KEY (teacher_id)
);

CREATE TABLE timetables (
	timetable_id serial NOT NULL,
	timetable_name character varying (50) NOT NULL,
	speciality_id int REFERENCES specialities (speciality_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	year_id int REFERENCES years (year_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	CONSTRAINT timetables__pkey PRIMARY KEY (timetable_id)
);

CREATE TABLE vocations (
	vocation_id serial NOT NULL,
	vocation_kind character varying (50) NOT NULL,
	vocation_applyingDate date NOT NULL,
	vocation_start date NOT NULL,
	vocation_end date NOT NULL,
	teacher_id int REFERENCES teachers (teacher_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	CONSTRAINT teachers__pkey PRIMARY KEY (teacher_id)
);

CREATE TABLE lessons (
	lesson_id serial NOT NULL,
	lesson_date date NOT NULL,
	teacher_id int REFERENCES teachers (teacher_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	subject_id int REFERENCES subjects (subject_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	audience_id int REFERENCES audiences (audience_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	lesson_time_id int REFERENCES lesson_times (lesson_time_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
	CONSTRAINT lessons__pkey PRIMARY KEY (lesson_id)
);
