package ru.netology.Diplom2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Files")
public class File {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size1")
    private Long size1;

    @Column(name = "data")
    private byte[] data;

//    @Column(name = "userName")
//    private String userName;
@ManyToOne
@JoinColumn(name = "login")
private User user;




}
