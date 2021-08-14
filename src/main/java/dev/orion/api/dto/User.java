package dev.orion.api.dto;

public class User {
    public String name;
    public String email;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
