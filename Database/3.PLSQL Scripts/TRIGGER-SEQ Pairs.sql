---------------------------------------Courses-----------------------------------------------
-- Sequence
drop sequence ADMIN.COURSES_SEQ;
CREATE SEQUENCE ADMIN.COURSES_SEQ
START WITH 10
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER;
-- Trigger
CREATE or replace TRIGGER ADMIN.COURSES_TRG
BEFORE INSERT
ON ADMIN.COURSES
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN
if :new.COURSE_ID is null then 
  :new.COURSE_ID := COURSES_SEQ.nextval;
  end if;
END COURSES_TRG;
 
------------------------------------------Student---------------------------------------------
-- Sequence
drop sequence ADMIN.STUDENTS_SEQ;
CREATE SEQUENCE ADMIN.STUDENTS_SEQ
START WITH 3
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER;
-- Trigger
CREATE or replace TRIGGER ADMIN.STUDENTS_TRG
BEFORE INSERT
ON ADMIN.STUDENTS
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
BEGIN
if  :new.STUDENT_ID  is null then 
  :new.STUDENT_ID := STUDENTS_SEQ.nextval;
end if;
END STUDENTS_TRG;
