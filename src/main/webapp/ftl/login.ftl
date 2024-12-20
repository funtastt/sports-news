<#include "base.ftl">
<#macro title>Login</#macro>
<#macro content>
    <div id="loginForm" class="login-form row d-flex justify-content-center">
        <div class="col-xl-7 col-lg-8 col-md-9 col-11">
            <h3>Login</h3>
            <form action="/login" method="post">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username"
                           name="username" class="form-control"
                           placeholder="Username"
                           required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password"
                           name="password" class="form-control"
                           placeholder="Password"
                           required>
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" id="remember_me" name="remember_me"
                           class="form-check-input">
                    <label for="remember_me"
                           class="form-check-label">Remember me
                    </label>
                </div>
                <a href="/registration" class="btn btn-primary" role="button">Register</a>

                <input type="submit" value="Enter" class="btn btn-primary">
            </form>
        </div>
    </div>
</#macro>
