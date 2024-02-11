create or replace trigger set_year before insert on enrollment for each row
declare 
begin
    if :new.academic_year is null then 
        select TO_CHAR(TO_DATE('2024', 'YYYY'), 'YYYY') into :new.academic_year from dual;
    end if; 
end;
show errors;