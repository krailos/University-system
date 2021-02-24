	INSERT INTO holidays (name, date) VALUES
('Holiday 1', DATE '2021-01-01'),
('Holiday 2', DATE '2021-01-02');

INSERT INTO buildings (name, address) VALUES 
('Building 1', 'Address 1'),
('Building 2', 'Address 2');

INSERT INTO audiences (number, building_id, capacity, description) VALUES
('1', '1','300', 'description1'),
('1', '2','120', 'description1'),
('2', '2','120', 'description2');

INSERT INTO university_office  (name, address) VALUES 
('Head office 1', 'Address 1'),
('Head office 2', 'Address 2');

INSERT INTO deans_office (name, university_office_id) VALUES 
('deans office 1', '1'),
('deans office 2', '1');

INSERT INTO departments (name) VALUES 
('department 1'),
('department 2');

INSERT INTO faculties (name, deans_office_id) VALUES 
('faculty 1', '1'),
('faculty 2', '1');

INSERT INTO specialities (name, faculty_id) VALUES 
('speciality 1', '1'),
('speciality 2', '1');

INSERT INTO years (name, speciality_id) VALUES 
('year 1', '1'),
('year 2', '1'),
('year 3', '1');

INSERT INTO groups (name, year_id) VALUES 
('group 1', '1'),
('group 2', '1');

INSERT INTO lessons_timeschedule (name) VALUES 
('lessons time schedule 1'),
('lessons time schedule 2');

INSERT INTO lesson_times (order_number, start_time, end_time, lessons_timeschedule_id) VALUES 
('first lesson', '08:30:00', '09:45:00', '1'),
('second lesson', '08:30:00', '09:45:00', '1');

INSERT INTO students (student_id, first_name, last_name, birth_date, phone,
address, email, rank, gender, group_id) VALUES 
('1id', 'student first name 1', 'student last name 1', DATE '2000-01-01', '0670000001', 'address 1', 'email 1', '0', 'MALE', '1' ),
('2id', 'student first name 2', 'student last name 2', DATE '2002-02-02', '0670000002', 'address 2', 'email 2', '0', 'FEMALE', '2' );

INSERT INTO subjects (name) VALUES 
('subject 1'),
('subject 2'),
('subject 3'),
('subject 4');

INSERT INTO teachers (teacher_id, first_name, last_name, birth_date, phone,
address, email, degree, gender, department_id) VALUES 
('1id', 'first name 1', 'last name 1', DATE '2000-01-01', '0670000001', 'address 1', 'email 1', '0', 'MALE', '1' ),
('2id', 'first name 2', 'last name 2', DATE '2002-02-02', '0670000002', 'address 2', 'email 2', '0', 'FEMALE', '1' );

INSERT INTO timetables (name) VALUES 
('timetable student for one date'),
('timetable student for month'),
('timetable teacher for one date'),
('timetable teacher for month');

INSERT INTO lessons (date, lesson_time_id, subject_id, teacher_id, audience_id) VALUES 
(DATE '2021-01-01', '1', '1', '1', '1'),
(DATE '2021-01-01', '2', '2', '2', '2'),
(DATE '2021-01-02', '1', '1', '1', '1'),
(DATE '2021-01-03', '1', '1', '1', '1'),
(DATE '2021-01-04', '1', '1', '1', '1'),
(DATE '2021-01-05', '1', '1', '1', '1');

INSERT INTO vocations (kind, applying_date, start_date, end_date, teacher_id) VALUES
('Vocation 1', DATE '2021-01-01', DATE '2021-02-02', DATE '2021-03-03', '1'),
('Vocation 2', DATE '2022-01-01', DATE '2022-02-02', DATE '2022-03-03', '2');

INSERT INTO lessons_groups (lesson_id, group_id) VALUES 
('1', '1'),
('1', '2'),
('2', '1'),
('2', '2');

INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES 
('1', '1'),
('1', '2'),
('2', '1'),
('2', '2');

INSERT INTO years_subjects (year_id, subject_id) VALUES 
('1', '1'),
('1', '2'),
('2', '3'),
('2', '4');