create or replace trigger delete_enrollment after delete on enrollment for each row
declare 
   student_record students%ROWTYPE;
   course_hours number(1);
    new_gpa number(3,2) ;
    course_gpa number(3,2);
begin
    if :old.GRADE_LETTER is not null then
            select * into student_record FROM students where student_id = :old.student_id;
            select CREDIT_HOURS into course_hours from courses where COURSE_ID = :old.course_id;
            select GRADE_GPA into course_gpa from grades where GRADE_LETTER =  :old.GRADE_LETTER ;
            
            if (student_record.TOTAL_HOURS -  course_hours)  != 0 then 
                 new_gpa := ((student_record.STUDENT_GPA * student_record.TOTAL_HOURS) - ( course_gpa *course_hours )) / (student_record.TOTAL_HOURS -  course_hours );
            else
                new_gpa := 0;
            end if ;
            update students set student_gpa = new_gpa , total_hours = total_hours - course_hours   where student_id = student_record.STUDENT_ID ;
    end if;
end;
show errors;