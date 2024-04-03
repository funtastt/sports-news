package ru.kpfu.itis.asadullin.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.kpfu.itis.asadullin.model.entity.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private int userId;
    private String username;
    private String password;
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
        this.password = user.getPassword();
    }

    private int calculateAge(Date dateOfBirth) {
        java.util.Date utilDate = new java.util.Date(dateOfBirth.getTime());

        LocalDate birthDate = utilDate.toInstant().atZone(Calendar.getInstance().getTimeZone().toZoneId()).toLocalDate();

        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(birthDate, currentDate);

        return period.getYears();
    }

    public boolean getIsMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean getIsAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}