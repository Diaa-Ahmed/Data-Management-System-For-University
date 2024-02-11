create or replace function Average_GPA_Course(v_course_id number)
return number
is
avg_gpa STUDENTS.STUDENT_GPA%type;
begin
    select avg(student_gpa) into avg_gpa from students inner join enrollment using(student_id) where course_id = v_course_id ;
    return avg_gpa ;
end;
