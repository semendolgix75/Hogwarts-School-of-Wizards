package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private final int BUFFER_SIZE = 1024;
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public Avatar uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student with id " + studentId + " is not found in database")
                );
        Path avatarPath = saveToLocalDirectory(student, avatarFile);
        Avatar avatar = saveToDb(student, avatarPath, avatarFile);

        return avatar;
    }

    @Override
    public Avatar findAvatar(Long avatarId) {
        return avatarRepository.findById(avatarId).orElseThrow(() ->
                new IllegalArgumentException("Avatar with id " + avatarId + " is not found in database")
        );
    }


    ////    Добавьте пагинацию для контроллер в AvatarController,
    // чтобы можно было получать списки аватарок постранично
    @Override
    public List<Avatar> getAllAvatarPaginated(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Path saveToLocalDirectory(Student student, MultipartFile avatarFile) throws IOException {
        Path avatarPath = Path.of(avatarsDir, "student" + student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(avatarPath.getParent());
        Files.deleteIfExists(avatarPath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(avatarPath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);
                BufferedOutputStream bos = new BufferedOutputStream(os, BUFFER_SIZE);
        ) {
            bis.transferTo(bos);
        }
        return avatarPath;
    }

    private Avatar saveToDb(Student student, Path avatarPath, MultipartFile avatarFile) throws IOException {
        Avatar avatar = getAvatarByStudentId(student.getId());
        avatar.setFilePath(avatarPath.toString());
        avatar.setFileSize(avatarFile.getSize());

        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        return avatarRepository.save(avatar);
    }

    private Avatar getAvatarByStudentId(long studentId) {
        return avatarRepository.findByStudent_id(studentId)
                .orElse(new Avatar());

    }
}





