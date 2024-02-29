create or replace trigger update_course_hours after update of CREDIT_HOURS on courses for each row
declare 
   Cursor enrolled_student  is SELECT STUDENT_ID, STUDENT_GPA, TOTAL_HOURS , GRADE_LETTER FROM students inner join enrollment using(STUDENT_ID) WHERE COURSE_ID = :old.COURSE_ID;
    new_gpa number(3,2) ;
    course_gpa number(3,2);
    new_hours number(3);
begin
    for student_record in enrolled_student loop 
            select GRADE_GPA into course_gpa from grades where GRADE_LETTER = student_record.GRADE_LETTER;
            new_hours := student_record.TOTAL_HOURS -  :old.CREDIT_HOURS + :new.CREDIT_HOURS;
            new_gpa := round(((student_record.STUDENT_GPA * student_record.TOTAL_HOURS) - ( course_gpa *:old.CREDIT_HOURS ) + (course_gpa * :new.CREDIT_HOURS) ) / ( new_hours ) , 2);
            update students set student_gpa = new_gpa , total_hours = new_hours   where student_id = student_record.STUDENT_ID ;
     end loop;
end;
show errors;