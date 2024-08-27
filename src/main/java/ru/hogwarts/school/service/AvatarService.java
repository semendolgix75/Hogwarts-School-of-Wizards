package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
//    String getExtensions(String fileName);
    Avatar uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long avatarId);

    //
    List<Avatar> getAllAvatarPaginated(int pageNumber, int pageSize);
}
