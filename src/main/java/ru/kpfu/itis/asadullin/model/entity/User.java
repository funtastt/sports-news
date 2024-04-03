package ru.kpfu.itis.asadullin.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Getter
    private String username;
    private String email;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    private String country;
    private String city;
    @Column(name = "registration_date")
    private Date registrationDate;
    @Column(name = "profile_picture")
    private String profilePicture;
    private String bio;
    @Column(name = "is_male")
    private boolean male;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
    public User(int userId) {
        this.userId = userId;
    }

    public User(int userId, String username, String email, String password, String firstName, String lastName, Date dateOfBirth, String country, String city, String profilePicture, String bio, boolean male) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.city = city;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.male = male;
    }

    public User(String username, String email, String password, String firstName, String lastName, Date dateOfBirth, String country, String city, boolean male, String bio) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.city = city;
        this.male = male;
        this.registrationDate = new Date(System.currentTimeMillis());
        this.bio = bio;
    }
}