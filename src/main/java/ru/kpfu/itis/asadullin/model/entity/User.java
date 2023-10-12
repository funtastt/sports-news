package ru.kpfu.itis.asadullin.model.entity;

import java.sql.Date;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String country;
    private String city;
    private Date registrationDate;
    private String profilePicture;
    private String bio;
    private boolean male;

    public User(int userId) {
        this.userId = userId;
    }

    public User(int userId, String username, String email, String password, String firstName, String lastName, Date dateOfBirth, String country, String city, Date registrationDate, String profilePicture, String bio, boolean male) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.city = city;
        this.registrationDate = registrationDate;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.male = male;
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

    public User(String username, String email, String password, String firstName, String lastName, Date dateOfBirth, String country, String city, boolean male) {
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
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }
}