package ru.hogwarts.school.model;

import jakarta.persistence.*;

@Entity
public class Avatar {
    @Id
    @GeneratedValue
    Long id;
    String filePath;
    long fileSize;
    String mediaType;
    @Lob
    byte[] data;
    @OneToOne
    Student student;

}
