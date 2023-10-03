<#include "base.ftl">
<#macro title>Register</#macro>
<#macro content>
    <div id="registrationForm" class="registration-form">
        <h3>Registration</h3>
        <form action="/registration" method="POST">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br><br>

            <!-- Optional Fields -->
            <label for="first_name">First Name:</label>
            <input type="text" id="first_name" name="first_name"><br><br>

            <label for="last_name">Last Name:</label>
            <input type="text" id="last_name" name="last_name"><br><br>

            <label for="date_of_birth">Date of Birth:</label>
            <input type="date" id="date_of_birth" name="date_of_birth"><br><br>

            <label>Gender:</label>
            <input type="radio" id="male" name="gender" value="M">
            <label for="male">Male</label>
            <input type="radio" id="female" name="gender" value="F">
            <label for="female">Female</label><br><br>

            <label for="country">Country:</label>
            <input type="text" id="country" name="country"><br><br>

            <label for="city">City:</label>
            <input type="text" id="city" name="city"><br><br>

            <input type="submit" value="Register">
        </form>
    </div>
</#macro>
