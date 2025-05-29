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
import ru.hogwarts.school.model.Faculty;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FcultyControllerRestTemplayTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void defaultMessage() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/school", String.class))
                .isEqualTo("Hogwarts it the beast school in the world!");
    }

    @Test
    void testGetFaculty() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/school", String.class))
                .isNotNull();
    }

    @Test
    void testPostFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Kegly");
        faculty.setColor("Red");

        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
                .isNotNull();
    }

    @Test
    void testPutFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Kegly");
        faculty.setColor("Red");

        ResponseEntity<Void> response = testRestTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                Void.class);

        Assertions
                .assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    void testDeleteFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Kegly");
        faculty.setColor("Red");

        ResponseEntity<Void> response = testRestTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.DELETE,
                new HttpEntity<>(faculty),
                Void.class);

        Assertions
                .assertThat(response.getStatusCode().equals(HttpStatus.OK));
    }
}
