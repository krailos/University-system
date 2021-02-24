
INSERT INTO holidays (name, date) VALUES 
('New Year', '01.01.2021'),
('Independant Day', '24.08.2021');

INSERT INTO buildings (name, address) VALUES 
('Main building', 'pr. Peremohy, 25'),
('Faculty of Finance building', 'st. I.Lepse, 12');

INSERT INTO audiences (number, building_id, capacity, description) VALUES
('1', '1','300', 'conference hall'),
('1', '2','120', 'common audience'),
('2', '2','120', 'common audience'),
('3', '2','120', 'common audience');

INSERT INTO university_office (name, address) VALUES 
('Head office', 'r. Peremohy, 25');

INSERT INTO deans_office (name, university_office_id) VALUES 
('deans of financial faculty', '1');

INSERT INTO departments (name) VALUES 
('department of finance');

INSERT INTO faculties (name, deans_office_id) VALUES 
('financial faculty', '1');

INSERT INTO specialities (name, faculty_id) VALUES 
('finance', '1'),
('banking', '1');

INSERT INTO years (name, speciality_id) VALUES 
('first', '1'),
('first', '2');

INSERT INTO groups (name, year_id) VALUES 
('group 1', '1'),
('group 2', '1'),
('group 1', '2'),
('group 2', '2');

INSERT INTO lessons_timeschedule (name) VALUES 
('schedule of lessons schedule');

INSERT INTO lesson_times (order_number, start_time, end_time, lessons_timeschedule_id) VALUES 
('first lesson', '08:30:00', '09:45:00', '1'),
('second lesson', '08:30:00', '09:45:00', '1');

INSERT INTO students (student_id, first_name, last_name, birth_date, phone,
address, email, rank, gender, group_id) VALUES 
('1f', 'student first name 1', 'student last name 1', '01.01.2000', '0670000001', 'address 1', 'email 1', '0', 'MALE', '1' ),
('1b', 'student first name 2', 'student last name 2', '02.01.2000', '0670000002', 'address 2', 'email 2', '0', 'MALE', '2' );

INSERT INTO subjects (name) VALUES 
('subject 1'),
('subject 2'),
('subject 3'),
('subject 4');

INSERT INTO teachers (teacher_id, first_name, last_name, birth_date, phone,
address, email, degree, gender, department_id ) VALUES 
('1t', 'teacher first name 1', 'teacher last name 1', '01.01.1970', '0680000001', 'address 1', 'email 1', 'professor', 'MALE', '1'),
('2t', 'teacher first name 2', 'teacher last name 2', '02.01.1970', '0680000002', 'address 2', 'email 2', 'professor', 'MALE', '1'),
('3t', 'teacher first name 3', 'teacher last name 3', '03.01.1970', '0680000003', 'address 3', 'email 3', 'professor', 'MALE', '1'),
('4t', 'teacher first name 4', 'teacher last name 4', '04.01.1970', '0680000004', 'address 4', 'email 4', 'professor', 'MALE', '1');

INSERT INTO timetables (name) VALUES 
('timetable for student by date date'),
('timetable for student by month'),
('timetable for teacher by date'),
('timetable for teacher by month');

INSERT INTO lessons (date, lesson_time_id, subject_id, teacher_id, audience_id) VALUES 
('01.02.2021', '1', '1', '1', '1'),
('01.02.2021', '2', '2', '2', '2'),
('01.02.2021', '1', '3', '3', '3'),
('01.02.2021', '2', '4', '4', '4');

INSERT INTO vocations (kind, applying_date, start_date, end_date, teacher_id) VALUES 
('annual', '01.10.2020', '01.08.2021', '14.08.2021', '1');

INSERT INTO lessons_groups (lesson_id, group_id) VALUES 
('1', '1'),
('1', '2'),
('2', '3'),
('2', '4');

INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES 
('1', '1'),
('1', '2'),
('2', '1'),
('3', '2'),
('4', '2');

INSERT INTO years_subjects (year_id, subject_id) VALUES 
('1', '1'),
('1', '2'),
('2', '3'),
('2', '4');