package ru.kpfu.itis.asadullin.controller.util.dto;

import ru.kpfu.itis.asadullin.model.dao.impl.FriendDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.asadullin.model.entity.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class UserDto {
    private int userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String country;
    private String city;
    private Date registrationDate;
    private String profilePicture;
    private boolean isMale;

    private String bio;

    private boolean isAdded;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = calculateAge(user.getDateOfBirth());
        this.country = user.getCountry();
        this.city = user.getCity();
        this.registrationDate = user.getRegistrationDate();
        this.profilePicture = user.getProfilePicture();
        this.isMale = user.isMale();
        this.bio = user.getBio();
    }

    private int calculateAge(Date dateOfBirth) {
        java.util.Date utilDate = new java.util.Date(dateOfBirth.getTime());

        LocalDate birthDate = utilDate.toInstant().atZone(Calendar.getInstance().getTimeZone().toZoneId()).toLocalDate();

        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(birthDate, currentDate);

        return period.getYears();
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public boolean getIsMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getIsAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}