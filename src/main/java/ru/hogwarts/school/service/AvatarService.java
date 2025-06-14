package ru.hogwarts.school.service;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
public class AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private static final String DIR_PATH = "avatars";

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.debug("The method for upload Avatar, was called");
        Path path = Path.of(DIR_PATH);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }

        Student student = studentService.findStudent(studentId);
        logger.debug("Was find Student with id " + studentId);

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
        logger.debug("The method for getting Extension, was called, with Path " + originalPath);
        if (!StringUtils.isBlank(originalPath)) {
            return originalPath.substring(originalPath.lastIndexOf(".") + 1);
        } else return null;
    }

    public Avatar getAvatarFromDb(Long studentId) {
        logger.debug("The method for getting Avatar from Data Base, was called");
        Student student = studentService.findStudent(studentId);
        return avatarRepository.findAvatarByStudent(student).orElseThrow();
    }

    public byte[] getAvatarFromLocal(Long studentId) {
        logger.debug("The method for getting Avatar from Local Base, was called");
        Student student = studentService.findStudent(studentId);
        Avatar avatar = avatarRepository.findAvatarByStudent(student).orElseThrow();
        String filePath = avatar.getFilePath();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            return bufferedInputStream.readAllBytes();
        } catch (IOException e) {
            logger.error("TWas called IllegalArgumentException with message");
            throw new IllegalArgumentException("Ошибка при загрузке аватара");
        }
    }

    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.debug("The method for getting all Avatar, was called");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
