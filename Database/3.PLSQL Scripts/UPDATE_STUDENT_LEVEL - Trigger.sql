create or replace trigger update_student_level before update of total_hours on students for each row
declare 
begin
    if :new.total_hours <= 40  then 
        :new.student_level := 1 ;
        elsif :new.total_hours <= 80  then
            :new.student_level := 2 ;
         elsif :new.total_hours <= 120 then
            :new.student_level := 3 ;
        else 
            :new.student_level := 4 ;
    end if; 
end;
show errors;