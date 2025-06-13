-- liquibase formated sql

-- changeset enot:1
CREATE INDEX student_name_index ON student (student_name);

-- changeset enot:2
CREATE INDEX faculty_color_name_index ON faculty (faculty_color, faculty_name);