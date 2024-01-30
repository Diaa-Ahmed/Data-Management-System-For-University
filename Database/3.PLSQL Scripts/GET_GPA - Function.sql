create or replace function get_gpa( s_id number , specific_year number DEFAULT NULL )
return number
is 
gpa number(3,2);
total_hours number(3);
begin
if specific_year is not null then
   select round(sum(credit_hours * grade_gpa)  / sum(credit_hours)  , 2 )  into gpa  from enrollment  inner join courses using(COURSE_ID) inner join grades using (GRADE_LETTER) 
    where STUDENT_ID = s_id group by academic_year ;
    return gpa;
else
    select student_gpa into gpa from students where student_id = s_id;
    return gpa;
end if;
end;
show errors;
