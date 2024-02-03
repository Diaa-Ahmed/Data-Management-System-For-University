/*
DROP TABLE  Enrollment;
DROP TABLE  Grades;
DROP TABLE  course_Prerequisites;
DROP TABLE  Courses;
DROP TABLE  student_phone;
DROP TABLE  students;
DROP TABLE  departments;
*/

Create table departments (
    department_id char(2) PRIMARY KEY,
    department_name varchar2(30) not null
);
create table students (
    student_id number(7) PRIMARY KEY ,
    first_name varchar2(20) not null ,
    last_name varchar2(20),
    city varchar2(20),
    street varchar2(20),
    building_no number(3),
    email varchar2(40) unique not null  , 
    account_password varchar2(20) not null ,
    student_level number(1)   default 1 not null  ,
    student_gpa number(3,2) default 0.0 ,
    total_hours number(3) default 0,
    major char(2) ,
    minor char(2),
    CONSTRAINT fk_major_department FOREIGN KEY (major) REFERENCES departments (department_id),
    CONSTRAINT fk_minor_department FOREIGN KEY (minor) REFERENCES departments (department_id) 
);


create table student_phone(
     student_id number(7),
     phone_number number(11) unique not null ,
     PRIMARY KEY (student_id, phone_number),
     CONSTRAINT fk_student_student_phone FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE Courses (
    course_id number(5) PRIMARY KEY,
    course_name VARCHAR2(80) NOT NULL,
    credit_hours number(1) default 3,
    minimum_level number(1) default 1,
    department_id char(2) NOT NULL ,
    CONSTRAINT fk_course_department FOREIGN KEY (department_id) REFERENCES Departments(department_id) ON DELETE CASCADE
);

CREATE TABLE course_Prerequisites (
    course_id number(5) not null ,
    prerequisite_id number(5) not null ,
    PRIMARY KEY (course_id, prerequisite_id),
    CONSTRAINT fk_course_has_prerequisite FOREIGN KEY (course_id) REFERENCES Courses(course_id) ON DELETE CASCADE,
    CONSTRAINT fk_prerequisite FOREIGN KEY (prerequisite_id) REFERENCES Courses(course_id) ON DELETE CASCADE
);

CREATE TABLE Grades (
    Grade_letter char(2) PRIMARY KEY , 
    min_degree number(2) ,
    max_degree number(3),
    grade_gpa number(3,2) not null
);

CREATE TABLE Enrollment (
    Student_id number(7) not null ,
    Course_id number(5) not null ,
    Grade_letter  char(2),
    academic_year number(4) not null ,
    PRIMARY KEY (course_id, Student_id , academic_year),
    CONSTRAINT fk_student_enrollment FOREIGN KEY (Student_id) REFERENCES Students(Student_id) ON DELETE CASCADE ,
    CONSTRAINT fk_course_enrollment FOREIGN KEY (Course_id) REFERENCES Courses(Course_id),
    CONSTRAINT fk_grade_enrollment FOREIGN KEY (Grade_letter) REFERENCES Grades(Grade_letter)
);