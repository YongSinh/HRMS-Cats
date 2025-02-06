package com.cats.attendanceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Table(name = "attachment")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class FileInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String fileId;

    @Column(name = "empid")
    private Long emId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "serviceType")
    private Integer serviceType;

    @Column(name = "file_name", columnDefinition = "varchar(50)")
    private String fileName;

    @Column(name = "file_type", columnDefinition = "varchar(50)")
    private String fileType;

    @Column(name = "dateCreated")
    private LocalDate dateCreated;
    @Column(name = "fileSize")
    private long fileSize;


    // This can return null if no file is stored
//    @Getter
//    @Column(name = "file" , columnDefinition = "longblob")
//    @Lob
//    private byte[] file;

}
