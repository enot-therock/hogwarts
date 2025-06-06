package ru.hogwarts.school.service;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private static final String DIR_PATH = "avatars";

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Path path = Path.of(DIR_PATH);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }

        Student student = studentService.findStudent(studentId);

        if (student != null && file != null && !file.isEmpty() && file.getOriginalFilename() != null) {
            String fileExtension = getExtension(file.getOriginalFilename());
            if (!StringUtils.isBlank(fileExtension)) {
                Path filePath = Path.of(DIR_PATH, student.getName() + "_" + student.getId() + "." + fileExtension);
                file.transferTo(filePath);

                Avatar newAvatar = new Avatar();

                newAvatar.setFilePath(filePath.toString());
                newAvatar.setFileSize(file.getSize());
                newAvatar.setMediaType(file.getContentType());
                newAvatar.setData(file.getBytes());
                newAvatar.setStudent(student);

                avatarRepository.save(newAvatar);
            }
        }
    }

    private String getExtension(String originalPath) {
        if (!StringUtils.isBlank(originalPath)) {
            return originalPath.substring(originalPath.lastIndexOf(".") + 1);
        } else return null;
    }

    public Avatar getAvatarFromDb(Long studentId) {
        Student student = studentService.findStudent(studentId);
        return avatarRepository.findAvatarByStudent(student).orElseThrow();
    }

    public byte[] getAvatarFromLocal(Long studentId) {
        Student student = studentService.findStudent(studentId);
        Avatar avatar = avatarRepository.findAvatarByStudent(student).orElseThrow();
        String filePath = avatar.getFilePath();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            return bufferedInputStream.readAllBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка при загрузке аватара");
        }
    }

    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
