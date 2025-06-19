ALTER TABLE student
    ADD CONSTRAINT age_constrain CHECK (age > 16),
    ADD CONSTRAINT name_unique UNIQUE (student_name),
    ALTER COLUMN student_name SET NOT NULL,
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT color_name_unique UNIQUE (faculty_color, faculty_name);
