package hu.szaniszlaid.webdemo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user_demo")
public class User extends BaseEntity {
    private String name;
    private String username;
    private String password;
    private String email;

}
