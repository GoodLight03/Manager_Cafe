package com.manager.cafe.POJO;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

//@NamedQuery(name="User.findByEmailId",query = "select u from User u where u.email=:email")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="user")
public class User implements Serializable{
    private static final long SerialVersionUIO=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    private String name;

    private String contactNumber;

    private String email;

    private String password;

    private String status;

    private String role;
}
