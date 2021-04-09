
INSERT INTO holidays (name, date) VALUES 
('New Year', '01.01.2021'),
('Independant Day', '24.08.2021');

INSERT INTO audiences (number, capacity, description) VALUES
('1','300', 'conference hall'),
('2','120', 'common audience'),
('3','120', 'common audience'),
('4','120', 'common audience');


INSERT INTO years (name) VALUES 
('first'),
('second');

INSERT INTO groups (name, year_id) VALUES 
('group 1', '1'),
('group 2', '1'),
('group 3', '1'),
('group 4', '1');


INSERT INTO lesson_times (order_number, start_time, end_time) VALUES 
('first lesson', '08:30:00', '09:45:00'),
('second lesson', '10:00:00', '10:45:00');

INSERT INTO students (student_id, first_name, last_name, birth_date, phone,
address, email, rank, gender, group_id) VALUES 
('1f', 'Taras', 'Slobodyan', '01.01.2000', '0670000001', 'address 1', 'abc1@gmail.com', '0', 'MALE', '1' ),
('2f', 'Ivan', 'Ivanyshyn', '01.01.2001', '0670000003', 'address 2', 'abc1@gmail.com', '0', 'MALE', '1' ),
('3f', 'Oleh', 'Zaycev', '01.01.2001', '0670000004', 'address 2', 'abc1@gmail.com', '0', 'MALE', '1' ),
('2b', 'Ihor', 'Klymenko', '01.01.2001', '0670000005', 'address 2', 'abc1@gmail.com', '0', 'MALE', '1' ),
('1b', 'Dmytro', 'Melnyk', '02.01.2000', '0670000002', 'address 2', 'abc1@gmail.com', '0', 'MALE', '2' );

INSERT INTO subjects (name) VALUES 
('economy'),
('financial system'),
('low'),
('banking system');

INSERT INTO teachers (teacher_id, first_name, last_name, birth_date, phone,
address, email, degree, gender) VALUES 
('1t', 'TeacherName-1', 'TeacherLastName-1', '01.01.1970', '0680000001', 'address 1', 'email 1', 'professor', 'MALE'),
('2t', 'TeacherName-2', 'TeacherLastName-2', '02.01.1970', '0680000002', 'address 2', 'email 2', 'professor', 'MALE'),
('3t', 'TeacherName-3', 'TeacherLastName-3', '03.01.1970', '0680000003', 'address 3', 'email 3', 'professor', 'MALE'),
('4t', 'TeacherName-4', 'TeacherLastName-4', '04.01.1970', '0680000004', 'address 4', 'email 4', 'professor', 'MALE');


INSERT INTO lessons (date, lesson_time_id, subject_id, teacher_id, audience_id) VALUES 
('01.02.2021', '1', '1', '1', '1'),
('01.02.2021', '2', '2', '2', '2'),
('01.02.2021', '1', '3', '3', '3'),
('01.02.2021', '2', '4', '4', '4');

INSERT INTO vocations (kind, applying_date, start_date, end_date, teacher_id) VALUES 
('GENERAL', '01.10.2020', '01.08.2021', '14.08.2021', '1');

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