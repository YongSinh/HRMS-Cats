package com.cats.attendanceservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "attachment")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Attachment {
    @Id
    @Column(name ="id")
    private Long fileId;
    @Column(name = "empid")
    private Long emId;
    @Column(name = "file_name" , columnDefinition = "varchar(50)")
    private String fileName;
    @Column(name = "file_type" , columnDefinition = "varchar(50)")
    private String fileType;
    @Column(name = "file" , columnDefinition = "blob")
    private Byte file;
}
