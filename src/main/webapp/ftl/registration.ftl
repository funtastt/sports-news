<#include "base.ftl">
<#macro title>Register</#macro>
<#macro content>
    <div id="registrationForm" class="registration-form row d-flex justify-content-center mb-20">
        <div class="col-xl-7 col-lg-8 col-md-9 col-11">
            <h3>Registration</h3>
            <form action="/registration" method="POST">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" class="form-control"
                           id="username" name="username"
                           required>
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" class="form-control"
                           id="email" name="email"
                           required>
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" class="form-control"
                           id="password" name="password"
                           required>
                </div>

                <div class="form-group">
                    <label for="first_name">First Name:</label>
                    <input type="text" class="form-control"
                           id="first_name" name="first_name">
                </div>

                <div class="form-group">
                    <label for="last_name">Last Name:</label>
                    <input type="text" class="form-control"
                           id="last_name" name="last_name">
                </div>

                <div class="form-group">
                    <label for="date_of_birth">Date of Birth:</label>
                    <input type="date" class="form-control"
                           id="date_of_birth" name="date_of_birth">
                </div>

                <div class="form-group">
                    <label>Gender:</label>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="male" value="M">
                        <label class="form-check-label" for="male">Male</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="female" value="F">
                        <label class="form-check-label" for="female">Female</label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="country">Country:</label>
                    <input type="text" class="form-control"
                           id="country" name="country">
                </div>

                <div class="form-group">
                    <label for="city">City:</label>
                    <input type="text" class="form-control"
                           id="city" name="city">
                </div>

                <a href="/login" class="btn btn-primary" role="button">Login</a>

                <button type="submit" class="btn btn-primary">Register</button>
            </form>
        </div>
    </div>
</#macro>
