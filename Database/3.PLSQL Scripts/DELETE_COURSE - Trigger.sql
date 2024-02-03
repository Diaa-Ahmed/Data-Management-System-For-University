create or replace trigger delete_course before delete on courses for each row
declare 
   Cursor enrolled_student  is SELECT STUDENT_ID, STUDENT_GPA, TOTAL_HOURS , GRADE_LETTER FROM students inner join enrollment using(STUDENT_ID) WHERE COURSE_ID = :old.COURSE_ID;
    new_gpa number(3,2) ;
    course_gpa number(3,2);
begin
    for student_record in enrolled_student loop 
            select GRADE_GPA into course_gpa from grades where GRADE_LETTER = student_record.GRADE_LETTER;
            new_gpa := round(((student_record.STUDENT_GPA * student_record.TOTAL_HOURS) - ( course_gpa *:old.CREDIT_HOURS )) / (student_record.TOTAL_HOURS -  :old.CREDIT_HOURS ) , 2);
            update students set student_gpa = new_gpa , total_hours = total_hours - :old.CREDIT_HOURS   where student_id = student_record.STUDENT_ID ;
     end loop;
     delete from enrollment where  COURSE_ID = :old.COURSE_ID;
end;
show errors;