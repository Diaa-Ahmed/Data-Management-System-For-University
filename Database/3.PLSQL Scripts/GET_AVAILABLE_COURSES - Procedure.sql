create or replace procedure get_available_courses (v_student_id number , result_set in OUT SYS_REFCURSOR ) is 
stud_lvl number(1);
begin
    select student_level into stud_lvl from students where student_id =v_student_id;
    open result_set for 
        with already_passed as(
        select course_id , grade_letter from enrollment where student_id = v_student_id and ( grade_letter != 'F'  or grade_letter is null)
        )
        select * from courses where course_id not in (
        select pre.course_id from COURSE_PREREQUISITES pre left join already_passed pass on PRE.PREREQUISITE_ID = pass.course_id  and grade_letter is not null where pass.course_id is null 
        union all
        select course_id from already_passed ) and MINIMUM_LEVEL <= stud_lvl ;
end;
show errors;