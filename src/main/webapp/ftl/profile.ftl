<#include "base.ftl">
<#macro title>Profile</#macro>
<#macro content>
    <div class="profile-form">
        <div class="row">
            <div class="col-md-6">
                <h3>Edit profile</h3>
                <form method="POST" action="/profile" id="editProfileForm">
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" class="form-control" id="username" name="username" value="${user.username}">
                    </div>

                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" value="${user.email}">
                    </div>

                    <div class="form-group">
                        <label for="firstName">First Name:</label>
                        <input type="text" class="form-control" id="firstName" name="first_name"
                               value="${user.firstName}">
                    </div>

                    <div class="form-group">
                        <label for="lastName">Last Name:</label>
                        <input type="text" class="form-control" id="lastName" name="last_name" value="${user.lastName}">
                    </div>

                    <div class="form-group">
                        <label for="dateOfBirth">Date of Birth:</label>
                        <input type="date" class="form-control" id="dateOfBirth" name="date_of_birth"
                               value="${user.dateOfBirth?string("yyyy-MM-dd")}">
                    </div>

                    <div class="form-group">
                        <label for="country">Country:</label>
                        <input type="text" class="form-control" id="country" name="country" value="${user.country}">
                    </div>

                    <div class="form-group">
                        <label>Gender:</label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="gender" id="male" value="M"
                                   <#if user.male>checked</#if>>
                            <label class="form-check-label" for="male">Male</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="gender" id="female" value="F"
                                   <#if !user.male>checked</#if>>
                            <label class="form-check-label" for="female">Female</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="city">City:</label>
                        <input type="text" class="form-control" id="city" name="city" value="${user.city}">
                    </div>

                    <div class="form-group">
                        <label for="bio">Bio:</label>
                        <input type="text" class="form-control" id="bio" name="bio" value="${user.bio}">
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>
            <div class="col-md-6">
                <h3>Change Password</h3>
                <form id="changePasswordForm" method="POST" action="/change-password">
                    <div class="form-group">
                        <label for="newPassword">Enter new password:</label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword">
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirm new password:</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
                    </div>
                    <button type="submit" class="btn btn-primary">Change Password</button>
                </form>

                <h3 class="change-profile">Change Profile Picture</h3>
                <form method="POST" action="/change-profile-picture" enctype="multipart/form-data"
                      id="changeProfilePictureForm">
                    <div class="text-center">
                        <img id="profileImage"
                             src="<#if user.profilePicture?has_content>${user.profilePicture}<#else>https://dummyimage.com/400x400/dee2e6/6c757d.jpg</#if>"
                             alt="Profile Picture" width="300" height="300">
                    </div>
                    <div class="form-group">
                        <label for="profilePicture">Upload new profile picture:</label>
                        <input type="file" class="form-control" id="profilePicture" name="profilePicture"
                               accept="image/*">
                    </div>
                    <button id="changeProfilePictureButton" type="button" class="btn btn-primary">Change Profile
                        Picture
                    </button>
                </form>
            </div>
        </div>
    </div>

    <script>
        $("#editProfileForm").submit(function (event) {
            event.preventDefault();

            let username = $("#username").val();
            let email = $("#email").val();
            let firstName = $("#firstName").val();
            let lastName = $("#lastName").val();
            let dateOfBirth = $("#dateOfBirth").val();
            let gender = $("input[name='gender']:checked").val();
            let country = $("#country").val();
            let city = $("#city").val();
            let bio = $("#bio").val();

            $.ajax({
                type: "POST",
                url: "/profile",
                data: {
                    "username": username,
                    "email": email,
                    "first_name": firstName,
                    "last_name": lastName,
                    "date_of_birth": dateOfBirth,
                    "gender": gender,
                    "country": country,
                    "city": city,
                    "bio": bio
                },
                success: function () {
                    alert("Successfully changed user data!")
                }
                ,
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        });

        $("#changePasswordForm").submit(function (event) {
            event.preventDefault();

            let newPassword = $("#newPassword").val()
            let confirmPassword = $("#confirmPassword").val()

            if (newPassword === confirmPassword) {
                $.ajax({
                    type: "POST",
                    url: "/profile",
                    data: {
                        "password": newPassword
                    },
                    success: function () {
                        alert("Successfully changed password!")
                        $("input[name='newPassword']").val('');
                        $("input[name='confirmPassword']").val('');

                    }
                    ,
                    error: function (error) {
                        console.error("Error:", error);
                    }
                });
            } else {
                alert("Passwords are not equal!")
            }
        });

        var selectedFile = null;

        $("#profilePicture").change(function () {
            if (this.files.length > 0) {
                selectedFile = this.files[0];
            }
        });

        $("#changeProfilePictureButton").click(function () {
            if (selectedFile) { // Проверяем, был ли выбран файл
                var formData = new FormData();
                formData.append("profilePicture", selectedFile);

                $.ajax({
                    type: "POST",
                    url: "/profile",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (resp) {
                        $("#profileImage").attr("src", resp);
                    },
                    error: function () {
                        console.error("Error changing profile picture.");
                    }
                });
            } else {
                alert("Please select a file to upload.");
            }
        });

    </script>
</#macro>