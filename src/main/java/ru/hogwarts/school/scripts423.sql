SELECT student.student_name, student.age, student.faculty_id, faculty.faculty_name
FROM student
INNER JOIN faculty ON student.faculty_id = faculty.id


SELECT student.student_name, student_id, avatar.student_id, avatar.file_path
FROM avatar
INNER JOIN student ON avatar.student_id = student.id