--liquibase formatted sql

--changeset sdolgih:1
        create index index_name_student on student(name);

--changeset sdolgih:2
        create index index_name_color_faculty on faculty(name,color);
