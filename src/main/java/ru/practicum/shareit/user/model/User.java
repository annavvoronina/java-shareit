package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, length = 200)
    @NotEmpty
    private String name;
    @Column(name = "email", nullable = false, unique = true, length = 30)
    private String email;

    public User(User newUser) {
        this.setId(newUser.getId());
        this.setName(newUser.getName());
        this.setEmail(newUser.getEmail());
    }
}
