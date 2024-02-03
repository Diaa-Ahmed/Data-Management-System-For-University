create or replace trigger cumulative_gpa after insert or update of GRADE_LETTER on enrollment for each row
declare 
hours number(3);
course_gpa number(3,2);
course_hours number(3);
prev_gpa number(3,2);
new_gpa number(3,2);
begin
    if :new.GRADE_LETTER is not null then
            select STUDENT_GPA, TOTAL_HOURS into prev_gpa , hours  from students where student_id = :new.student_id;
            select credit_hours into course_hours from courses where course_id = :new.course_id;
            select grade_gpa into course_gpa from grades where grade_letter = :new.grade_letter;
            
            new_gpa := round(((prev_gpa * hours) + (course_gpa *course_hours )) / ( course_hours +hours) , 2);
            update students set student_gpa = new_gpa , total_hours = course_hours +hours  where student_id =:new.student_id;
    end if;
end;
show errors;