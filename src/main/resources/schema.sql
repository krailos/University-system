DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS courses CASCADE; 
DROP TABLE IF EXISTS students_courses CASCADE;
DROP TABLE IF EXISTS holidays CASCADE;

CREATE TABLE holidays (
	holiday_id serial NOT NULL,
	holiday_name character varying (50) NOT NULL,
	holiday_date date NOT NULL
);

CREATE TABLE groups (
	group_id serial NOT NULL,
    group_name character varying (50) NOT NULL,
    CONSTRAINT groups__pkey PRIMARY KEY (group_id)
    );
    
CREATE TABLE students (
    student_id serial NOT NULL,
    group_id integer,
    first_name character varying (50) NOT NULL,
    last_name character varying (50) NOT NULL,
    CONSTRAINT students__pkey PRIMARY KEY (student_id)
    );
    
CREATE TABLE courses (
    course_id serial NOT NULL,
    course_name character varying (50) NOT NULL,
    course_description character varying (50) NOT NULL,
    CONSTRAINT courses__pkey PRIMARY KEY (course_id)
    );
    
CREATE TABLE students_courses (
	student_id int REFERENCES students (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
	course_id int REFERENCES courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE (student_id, course_id)
	);
	
INSERT INTO holidays (holiday_name, holiday_date) VALUES 
('New Year', '01.01.2021'),
('Independant Day', '24.08.2021');
