package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.model.Student;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest {

	@LocalServerPort
	private int port;

	@Autowired
	private StudentController studentController;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() throws Exception {
		Assertions.assertThat(studentController).isNotNull();
	}

	@Test
	public void testDefaultMessage() throws Exception {
		Assertions
				.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/school", String.class))
				.isEqualTo("Hogwarts it the beast school in the world!");
	}

	@Test
	public void testGetStudent() throws Exception {
		Assertions
				.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class))
				.isNotNull();
	}

	@Test
	public void testPostStudent() throws Exception {
		Student student = new Student();
		student.setName("Vova");
		student.setAge(37);

		Assertions
				.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
				.isNotNull();
	}

	@Test
	public void testPutStudent() throws Exception {
		Student student = new Student();
		student.setName("German");
		student.setAge(37);

		ResponseEntity<Void> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/student",
				HttpMethod.PUT,
				new HttpEntity<>(student),
				Void.class);

		Assertions.assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
	}

	@Test
	public void testDeleteStudent() throws Exception {
		Student student = new Student();
		student.setName("German");
		student.setAge(37);

		ResponseEntity<Void> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/student",
				HttpMethod.DELETE,
				new HttpEntity<>(student),
				Void.class);

		Assertions.assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
	}
}
