package com.roy.grpc.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
@Data
@ToString
public class User {

    @Id
    private String login;
    private String name;
    private String genre;
}
