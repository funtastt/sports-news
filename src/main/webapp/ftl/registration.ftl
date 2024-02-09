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
                    <div class="invalid-feedback">
                        Please enter your username.
                    </div>
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
                    <div class="invalid-feedback">
                        The password must contain at least 8 characters, at least one uppercase letter, one lowercase letter and one digit.
                    </div>
                </div>

                <div class="form-group">
                    <label for="first_name">First Name:</label>
                    <input type="text" class="form-control"
                           id="first_name" name="first_name"
                           required>
                </div>

                <div class="form-group">
                    <label for="last_name">Last Name:</label>
                    <input type="text" class="form-control"
                           id="last_name" name="last_name"
                           required>
                </div>

                <div class="form-group">
                    <label for="date_of_birth">Date of Birth:</label>
                    <input type="date" class="form-control"
                           id="date_of_birth" name="date_of_birth"
                           required>
                </div>

                <div class="form-group">
                    <label>Gender:</label>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gender" id="male" value="M" checked="checked">
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
                           id="country" name="country"
                           required>
                </div>

                <div class="form-group">
                    <label for="city">City:</label>
                    <input type="text" class="form-control"
                           id="city" name="city"
                           required>
                </div>

                <a href="/login" class="btn btn-primary" role="button">Login</a>

                <button type="submit" class="btn btn-primary">Register</button>
            </form>
        </div>
    </div>

    <script>
        $("#registrationForm").on("submit", function(event) {
            event.preventDefault()
            var password = $("#password").val();

            if (!isPasswordValid(password)) {
                event.preventDefault();
                $("#password").addClass("is-invalid");
                return;
            }

            $.ajax({
                type: "POST",
                url: "/registration",
                data: {
                    "username" : $("#username").val(),
                    "email" : $("#email").val(),
                    "password" : password,
                    "first_name" : $("#first_name").val(),
                    "last_name" : $("#last_name").val(),
                    "date_of_birth" : $("#date_of_birth").val(),
                    "gender" : $("input[name='gender']:checked").val(),
                    "country" : $("#country").val(),
                    "city" : $("#city").val()
                },
                success: function(response) {
                    if (response === false) {
                        alert('User with this username or email already exists!')
                    }
                },
                error: function(error) {
                    // Обработка ошибок
                    console.error("Error:", error);
                }
            });
        });

        function isPasswordValid(password) {
            const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
            return passwordPattern.test(password);
        }
    </script>
</#macro>
