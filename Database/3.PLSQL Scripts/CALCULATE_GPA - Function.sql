create or replace function calculate_gpa( s_id number , hours out number , specific_year number default null )
return number
is 
gpa number(3,2);
total_hours number(3);
begin
if specific_year is not null then
    select round(sum(credit_hours * grade_gpa)  / sum(credit_hours)  , 2 ) ,  sum(credit_hours)   into gpa , total_hours   from enrollment  inner join courses using(COURSE_ID) inner join grades using (GRADE_LETTER) 
    where STUDENT_ID = s_id and academic_year = specific_year  group by academic_year ;
    hours := total_hours ;
    return gpa;
else
     select round(sum(credit_hours * grade_gpa)  / sum(credit_hours)  , 2 ) ,  sum(credit_hours)   into gpa , total_hours   from enrollment  inner join courses using(COURSE_ID) inner join grades using (GRADE_LETTER) 
    where STUDENT_ID = s_id  group by student_id ;
    hours := total_hours ;
    return gpa;
end if;
end;
show errors;
