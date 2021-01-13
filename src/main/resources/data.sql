
INSERT INTO holidays (holiday_name, holiday_date) VALUES 
('New Year', '01.01.2021'),
('Independant Day', '24.08.2021');

INSERT INTO buildings (building_name, building_address) VALUES 
('Main building', 'pr. Peremohy, 25'),
('Faculty of Finance building', 'st. I.Lepse, 12');

INSERT INTO audiences (audience_number, building_id, audience_capacity, audience_description) VALUES
('1', '1','300', 'conference hall'),
('1', '2','120', 'common audience'),
('2', '2','120', 'common audience'),
('3', '2','120', 'common audience');

INSERT INTO university_office (university_office_name, university_office_address) VALUES 
('Head office', 'r. Peremohy, 25');

INSERT INTO deans_office (deans_office_name, university_office_id) VALUES 
('deans of financial faculty', '1');

INSERT INTO departments (department_name) VALUES 
('department of finance');

INSERT INTO faculties (faculty_name, deance_office_id) VALUES 
('financial faculty', '1');

INSERT INTO specialities (speciality_name, faculty_id) VALUES 
('finance', '1'),
('banking', '1');

INSERT INTO years (year_name, speciality_id) VALUES 
('year first', '1'),
('year first', '2');

INSERT INTO groups (group_name, year_id) VALUES 
('group first', '1'),
('group first', '2');

INSERT INTO lessons_timeschedule (lessons_timeschedule_name) VALUES 
('schedule of lessons schedule');

INSERT INTO lesson_times (lesson_time_order, lesson_time_starttime, lesson_time_endtime, lessons_timeschedule_id) VALUES 
('first lesson', '08:30:00', '09:45:00', '1'),
('second lesson', '08:30:00', '09:45:00', '1');

INSERT INTO students (student_studentid, student_first_name, student_last_name, student_birthday, student_phone,
student_address, student_email, student_rank, student_gender, group_id) VALUES 
('1f', 'student first name 1', 'student last name 1', '01.01.2000', '0670000001', 'address 1', 'email 1', '0', 'male', '1' ),
('1b', 'student first name 2', 'student last name 2', '02.01.2000', '0670000002', 'address 2', 'email 2', '0', 'male', '2' );

INSERT INTO subjects (subject_name) VALUES 
('subject 1'),
('subject 2'),
('subject 3'),
('subject 4');

INSERT INTO teachers (teacher_teacherid, teacher_first_name, teacher_last_name, teacher_birthday, teacher_phone,
teacher_address, teacher_email, teacher_degree, teacher_gender, department_id ) VALUES 
('1t', 'teacher first name 1', 'teacher last name 1', '01.01.1970', '0680000001', 'address 1', 'email 1', 'professor', 'male', '1'),
('2t', 'teacher first name 2', 'teacher last name 2', '02.01.1970', '0680000002', 'address 2', 'email 2', 'professor', 'male', '1'),
('3t', 'teacher first name 3', 'teacher last name 3', '03.01.1970', '0680000003', 'address 3', 'email 3', 'professor', 'male', '1'),
('4t', 'teacher first name 4', 'teacher last name 4', '04.01.1970', '0680000004', 'address 4', 'email 4', 'professor', 'male', '1');

INSERT INTO timetables (timetable_name, year_id) VALUES 
('timetable for first year finance', '1'),
('timetable for first year banking', '2');

INSERT INTO lessons (lesson_date, lesson_time_id, subject_id, teacher_id, audience_id, group_id, timetable_id) VALUES 
('01.02.2021', '1', '1', '1', '1', '1', '1'),
('01.02.2021', '2', '2', '2', '2', '1', '1'),
('01.02.2021', '1', '3', '3', '3', '2', '2'),
('01.02.2021', '2', '4', '4', '4', '2', '2');

INSERT INTO vocations (vocation_kind, vocation_applyingDate, vocation_start, vocation_end, teacher_id) VALUES 
('annual', '01.10.2020', '01.08.2021', '14.08.2021', '1');
