-- Insert data into departments

INSERT INTO departments (department_id, department_name) VALUES ('CS', 'Computer Science');
INSERT INTO departments (department_id, department_name) VALUES ('IS', 'Information System');
INSERT INTO departments (department_id, department_name) VALUES ('DS', 'Data Science');
INSERT INTO departments (department_id, department_name) VALUES ('GN', 'General Department');

-- Insert data into students

INSERT INTO students (student_id, first_name, last_name, email, account_password) 
VALUES (1, 'Diaa', 'Ahmed', 'diaaahmed38@gmail.com', 'root');
INSERT INTO students (student_id, first_name, last_name, email, account_password , student_level) 
VALUES (2, 'Maged', 'Magdy', 'mego@gmail.com', '123' , 2);

-- Insert data into student_phone

INSERT INTO student_phone (student_id, phone_number) VALUES (1, 01098238065);
INSERT INTO student_phone (student_id, phone_number) VALUES (1, 01111111111);
INSERT INTO student_phone (student_id, phone_number) VALUES (2, 01555555551);

-- Insert data into Courses

INSERT INTO Courses (course_id, course_name, credit_hours, minimum_level, department_id) 
VALUES (1, 'Introduction to Programming', 3, 1, 'CS');
INSERT INTO Courses (course_id, course_name, credit_hours, minimum_level, department_id) 
VALUES (2, 'Object Oriented Programming', 3, 2, 'CS');
INSERT INTO Courses (course_id, course_name, credit_hours, minimum_level, department_id) 
VALUES (3, 'Functional Programming', 3, 3, 'CS');
INSERT INTO Courses (course_id, course_name, credit_hours, minimum_level, department_id) 
VALUES (4, 'Database Management Systems', 3, 1, 'IS');
INSERT INTO Courses (course_id, course_name, credit_hours, minimum_level, department_id) 
VALUES (5, 'Web Development', 3, 1, 'IS');
INSERT INTO Courses (course_id, course_name, credit_hours, minimum_level, department_id) 
VALUES (6, 'Data Visualization', 3, 1, 'DS');
INSERT INTO Courses (course_id, course_name, credit_hours, minimum_level, department_id) 
VALUES (7, 'Statistics', 3, 1, 'DS');
INSERT INTO Courses (course_id, course_name, credit_hours, minimum_level, department_id) 
VALUES (8, 'Human Resources', 2, 1, 'GN');
INSERT INTO Courses (course_id, course_name, credit_hours, minimum_level, department_id) 
VALUES (9, 'Project Management', 3, 1, 'GN');

-- Insert data into course_Prerequisites

INSERT INTO course_Prerequisites  VALUES (2,1);
INSERT INTO course_Prerequisites  VALUES (3,2);

-- Insert data into Grades

INSERT INTO Grades (Grade_letter, min_degree, max_degree, grade_gpa) VALUES ('A+', 90, 100, 4.0);
INSERT INTO Grades (Grade_letter, min_degree, max_degree, grade_gpa) VALUES ('A', 85, 90, 3.75);
INSERT INTO Grades (Grade_letter, min_degree, max_degree, grade_gpa) VALUES ('B+', 80, 85, 3.4);
INSERT INTO Grades (Grade_letter, min_degree, max_degree, grade_gpa) VALUES ('B', 75, 80, 3.1);
INSERT INTO Grades (Grade_letter, min_degree, max_degree, grade_gpa) VALUES ('C+', 70, 75, 2.75);
INSERT INTO Grades (Grade_letter, min_degree, max_degree, grade_gpa) VALUES ('C', 65, 70, 2.5);
INSERT INTO Grades (Grade_letter, min_degree, max_degree, grade_gpa) VALUES ('D+', 60, 65, 2.2);
INSERT INTO Grades (Grade_letter, min_degree, max_degree, grade_gpa) VALUES ('D', 50, 60, 2);
INSERT INTO Grades (Grade_letter, min_degree, max_degree, grade_gpa) VALUES ('F', 0, 50, 1);

-- Insert data into Enrollment

-- Student 1

INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (1, 1, 'F' , 2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (1, 4, 'A', 2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (1, 5, 'B+', 2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (1, 6, 'C+', 2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (1, 7, 'D+', 2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (1, 8, 'B', 2023);

-- Student 2

INSERT INTO Enrollment (Student_id, Course_id,  academic_year) VALUES (2, 2, 2024);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (2, 1, 'A' ,2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (2, 4, 'C', 2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (2, 5, 'B', 2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (2, 6, 'D', 2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (2, 7, 'B+', 2023);
INSERT INTO Enrollment (Student_id, Course_id, grade_letter, academic_year) VALUES (2, 8, 'A+', 2023);

