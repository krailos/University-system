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
('year 2', '1');

INSERT INTO groups (name, year_id) VALUES 
('group 1', '1'),
('group 2', '1');

INSERT INTO lessons_timeschedule (name) VALUES 
('lessons time schedule 1'),
('lessons time schedule 2');

INSERT INTO lesson_times (order_number, start_time, end_time, lessons_timeschedule_id) VALUES 
('first lesson', '08:30:00', '09:45:00', '1'),
('second lesson', '08:30:00', '09:45:00', '1');
