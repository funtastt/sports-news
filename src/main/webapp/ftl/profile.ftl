<#include "base.ftl">
<#macro title>Profile</#macro>
<#macro content>
    <style>
        h5 {
            color: #000;
        }

        .tab-content {
            border: none;
            padding: 0;
        }
    </style>

    <ul class="nav justify-content-center">
        <li class="tab-button" onclick="showTab('editProfileForm')">Edit Profile</li>
        <li class="tab-button" onclick="showTab('changePasswordForm')">Change Password</li>
        <li class="tab-button" onclick="showTab('changeProfilePictureForm')">Change Profile Picture</li>
    </ul>

    <div class="tab-content nav-link" id="editProfileForm">
        <div class="container-fluid px-1 py-5 mx-auto">
            <div class="row d-flex justify-content-center">
                <div class="col-xl-7 col-lg-8 col-md-9 col-11 text-center">
                    <div class="card">
                        <h5 class="text-center mb-4">Edit profile details</h5>
                        <form class="form-card" method="POST" action="/profile" id="editProfileForm">
                            <div class="row justify-content-between text-left">
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">Username:</label>
                                    <input type="text" id="username" name="username" class="form-control"
                                           value="${user.username}">
                                </div>
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">Email:</label>
                                    <input type="email" id="email" name="email" class="form-control"
                                           value="${user.email}">
                                </div>
                            </div>
                            <div class="row justify-content-between text-left">
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">First Name:</label>
                                    <input type="text" id="firstName" name="first_name" class="form-control"
                                           value="${user.firstName}">
                                </div>
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">Last Name:</label>
                                    <input type="text" id="lastName" name="last_name" class="form-control"
                                           value="${user.lastName}">
                                </div>
                            </div>
                            <div class="row justify-content-between text-left">
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">Date of Birth:</label>
                                    <input type="date" id="dateOfBirth" name="date_of_birth" class="form-control"
                                           value="${user.dateOfBirth?string("yyyy-MM-dd")}">
                                </div>
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">Country:</label>
                                    <input type="text" id="country" name="country" class="form-control"
                                           value="${user.country}">
                                </div>
                            </div>
                            <div class="row justify-content-between text-left">
                                <div class="form-group col-12 d-flex align-items-center">
                                    <label class="form-control-label px-3">Gender:</label>
                                    <div class="form-check form-check-inline ml-3">
                                        <input class="form-check-input" type="radio" id="male" name="gender" value="M"
                                               <#if user.male>checked</#if>>
                                        <label class="form-check-label" for="male">Male</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" id="female" name="gender" value="F"
                                               <#if !user.male>checked</#if>>
                                        <label class="form-check-label" for="female">Female</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row justify-content-between text-left">
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">City:</label>
                                    <input type="text" id="city" name="city" class="form-control" value="${user.city}">
                                </div>
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">Bio:</label>
                                    <input type="text" id="bio" name="bio" class="form-control" value="${user.bio}">
                                </div>
                            </div>
                            <div class="row justify-content-end">
                                <div class="form-group col-sm-6">
                                    <button type="submit" class="btn-block btn-primary">Update</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="tab-content nav-link" id="changePasswordForm">
        <div class="container-fluid px-1 py-5 mx-auto">
            <div class="row d-flex justify-content-center">
                <div class="col-xl-7 col-lg-8 col-md-9 col-11 text-center">
                    <div class="card">
                        <h5 class="text-center mb-4">Change Your Password</h5>
                        <form class="form-card" method="POST" action="/profile" id="changePasswordForm">
                            <div class="row justify-content-between text-left">
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">Enter new password:</label>
                                    <input type="password" id="newPassword" name="newPassword" class="form-control">
                                </div>
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">Confirm new password:</label>
                                    <input type="password" id="confirmPassword" name="confirmPassword"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="row justify-content-end">
                                <div class="form-group col-sm-6">
                                    <button type="submit" class="btn-block btn-primary">Change Password</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="tab-content nav-link" id="changeProfilePictureForm">
        <div class="container-fluid px-1 py-5 mx-auto">
            <div class="row d-flex justify-content-center">
                <div class="col-xl-7 col-lg-8 col-md-9 col-11 text-center">
                    <div class="card">
                        <h5 class="text-center mb-4">Change Your Profile Picture</h5>
                        <form class="form-card" method="POST" action="/profile" enctype="multipart/form-data"
                              id="changeProfilePictureForm">
                            <div class="row justify-content-center text-center">
                                <div class="form-group col-12">
                                    <img id="profileImage" class="rounded-circle"
                                         src="<#if user.profilePicture?has_content>${user.profilePicture}<#else>https://dummyimage.com/400x400/dee2e6/6c757d.jpg</#if>"
                                         alt="Profile Picture" width="300" height="300">
                                </div>
                            </div>
                            <div class="row justify-content-center">
                                <div class="form-group col-sm-6 flex-column d-flex">
                                    <label class="form-control-label px-3">Upload New Profile Picture:</label>
                                    <input type="file" id="profilePicture" name="profilePicture" class="form-control"
                                           accept="image/*">
                                </div>
                            </div>
                            <div class="row justify-content-end">
                                <div class="form-group col-sm-6">
                                    <button type="submit" class="btn-block btn-primary">Change Profile Picture</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script>
        function showTab(tabId) {
            const tabs = document.querySelectorAll('.tab-content');
            tabs.forEach(tab => tab.style.display = 'none');
            document.getElementById(tabId).style.display = 'block';
        }

        showTab('editProfileForm');

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

        $("#changeProfilePictureForm").submit(function (event) {
            event.preventDefault();

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