<#include "base.ftl">
<#macro title>Login</#macro>
<#macro content>
    <div id="loginForm" class="login-form">
        <h3>Login</h3>
        <form action="/login" method="post">
            <div class="form-group"><label for="username">Username:</label>
                <input type="text" id="username"
                       name="username" class="form-control"
                       required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password"
                       name="password" class="form-control"
                       required>
            </div>
            <div class="form-group form-check">
                <input type="checkbox" id="remember_me" name="remember_me"
                       class="form-check-input">
                <label for="remember_me"
                       class="form-check-label">Remember
                    me
                </label>
            </div>
            <input type="submit" value="Enter" class="btn"></form>
    </div>
</#macro>
