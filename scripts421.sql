--Необходимо для них создать следующие ограничения:
-- Возраст студента не может быть меньше 16 лет.
alter table public.student
add constraint age_check_student check(age >= 16);
-- Имена студентов должны быть уникальными и не равны нулю.
alter table public.student
add constraint name_unique_student unique(name);
--Пара “значение названия” - “цвет факультета” должна быть уникальной.
alter table public.faculty
add constraint name_unique_color unique (name, color);
--При создании студента без возраста ему автоматически должно присваиваться 20 лет.
alter table public.student alter column age set default 20;
