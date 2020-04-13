package ru.stqa.pft.mantis.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity //сущность, для привязки к БД
@Table(name = "mantis_user_table")

public class User {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Type(type = "short")
  private short accessLevel;


  public int getId() {
    return id;
  }

  public User setId(int id) {
    this.id = id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public User setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public int getAccessLevel() {
    return accessLevel;
  }

  public User setAccessLevel(short accessLevel) {
    this.accessLevel = accessLevel;
    return this;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", accessLevel=" + accessLevel +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id == user.id &&
            accessLevel == user.accessLevel &&
            Objects.equals(username, user.username) &&
            Objects.equals(email, user.email) &&
            Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email, password, accessLevel);
  }
}
