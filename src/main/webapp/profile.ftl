<#include "base.ftl">
<#macro title>Profile</#macro>
<#macro content>
    <h1>Edit Profile</h1>
    <form method="POST" action="/profile">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username"><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email"><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password"><br>

        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="first_name"><br>

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="last_name"><br>

        <label for="dateOfBirth">Date of Birth:</label>
        <input type="date" id="dateOfBirth" name="date_of_birth"><br>

        <label for="country">Country:</label>
        <input type="text" id="country" name="country"><br>

        <label for="city">City:</label>
        <input type="text" id="city" name="city"><br>

        <label for="bio">Bio:</label>
        <input type="text" id="bio" name="bio"><br>

        <label>Gender:</label>
        <input type="radio" id="male" name="gender" value="M">
        <label for="male">Male</label>
        <input type="radio" id="female" name="gender" value="F">
        <label for="female">Female</label><br><br>

        <input type="submit" value="Update">
    </form>
</#macro>
