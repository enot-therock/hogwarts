package ru.hogwarts.school;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SchoolApplicationTests {
}

//import org.apache.tomcat.util.http.parser.MediaType;
//import org.assertj.core.api.Assertions;
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.assertj.MockMvcTester;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import ru.hogwarts.school.controller.StudentController;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.repository.FacultyRepository;
//import ru.hogwarts.school.repository.StudentRepository;
//import ru.hogwarts.school.service.StudentService;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest
//public class SchoolApplicationTests {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockitoBean
//	private StudentRepository studentRepository;
//
//	@MockitoBean
//	private FacultyRepository facultyRepository;
//
//	@MockitoSpyBean
//	private StudentService studentService;
//
//	@InjectMocks
//	private StudentController studentController;
//
//	@Test
//	public void saveStudentTest() throws Exception {
//		final String name = "Harry";
//		final int age = 20;
//		final long id = 1;
//
//		JSONObject studentObject = new JSONObject();
//		studentObject.put("name", name);
//		studentObject.put("age", age);
//
//		Student student = new Student();
//		student.setId(id);
//		student.setName(name);
//		student.setAge(age);
//
//		when(studentRepository.save(any(Student.class))).thenReturn(student);
//		when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
//
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/student")
//				.content(studentObject.toString())
//				.contentType(MediaType)
//				.accept(MediaType.parseMediaTypeOnly())
//				.andExpect(status().isOk())
//				.andExprct(jsonPath("$.id").value(id))
//				.andExprct(jsonPath("$.name").value(name))
//				.andExprct(jsonPath("$.age").value(age)));
//	}
//}

