create or replace function Average_GPA_Course(v_course_id number)
return number
is
avg_gpa STUDENTS.STUDENT_GPA%type;
begin
    select avg(course_grade) into avg_gpa from (
        select (select grade_gpa from grades  where grade_letter = enrollment.grade_letter ) as course_grade
             from students inner join enrollment using(student_id) where course_id = v_course_id  ) ;
    return avg_gpa ;
end;
