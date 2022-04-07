package cash.entity;

import java.util.Objects;

public class User extends Entity {
    private Integer idUser;
    private String login;
    private String password;
    private String surname;
    private String phoneNumber;
    private String email;
    private String address;
    private Role role;

    public User() {
    }

    public User(String login, String password, String surname, Role role) {
        this.login = login;
        this.password = password;
        this.surname = surname;
        this.role = role;
    }

    public User(String login, String password, String surname, String phoneNumber, String email, String address, Role role) {
        this.login = login;
        this.password = password;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public Integer getId() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getPassword(), user.getPassword())
                && Objects.equals(getLogin(), user.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + idUser +
                ", login='" + login + '\'' +
                ", role=" + role +
                ", pass=" + password +
                ", surname=" + surname +
                '}';
    }
}
