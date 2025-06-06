package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {   // - @RequiredArgsConstructor
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/upload/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadAvatar(@PathVariable("studentId") Long studentId, @RequestBody MultipartFile file) throws IOException {
        avatarService.uploadAvatar(studentId, file);
    }

    @GetMapping("/from_db")
    public ResponseEntity<byte[]> getAvatarFromDb(@RequestParam("studentId") Long studentId) {
        Avatar avatar = avatarService.getAvatarFromDb(studentId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(avatar.getMediaType())).body(avatar.getData());
    }

    @GetMapping(value = "/from_local", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAvatarFromLocal(@RequestParam("studentId") Long studentId) {
        return avatarService.getAvatarFromLocal(studentId);
    }

    @GetMapping("/getAllAvatars")
    public ResponseEntity<List<Avatar>> getAllAvatars(@RequestParam("page") Integer pageNumber, @RequestParam("size") Integer pageSize) {
        List<Avatar> avatarList = avatarService.getAllAvatars(pageNumber, pageSize);
        return ResponseEntity.ok(avatarList);
    }
}
