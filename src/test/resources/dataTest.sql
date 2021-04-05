	INSERT INTO holidays (name, date) VALUES
('Holiday 1', DATE '2021-01-01'),
('Holiday 2', DATE '2021-01-02');


INSERT INTO audiences (number, capacity, description) VALUES
('1','300', 'description1'),
('2','120', 'description2'),
('3','120', 'description3');


INSERT INTO years (name) VALUES 
('year 1'),
('year 2'),
('year 3');

INSERT INTO groups (name, year_id) VALUES 
('group 1', '1'),
('group 2', '1');


INSERT INTO lesson_times (order_number, start_time, end_time) VALUES 
('first lesson', '08:30:00', '09:15:00'),
('second lesson', '09:30:00', '10:15:00');

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
address, email, degree, gender) VALUES 
('1id', 'first name 1', 'last name 1', DATE '2000-01-01', '0670000001', 'address 1', 'email 1', '0', 'MALE' ),
('2id', 'first name 2', 'last name 2', DATE '2002-02-02', '0670000002', 'address 2', 'email 2', '0', 'FEMALE' );

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
('GENERAL', DATE '2021-01-01', DATE '2021-02-02', DATE '2021-02-010', '1'),
('GENERAL', DATE '2022-01-01', DATE '2022-02-02', DATE '2022-02-010', '2');

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