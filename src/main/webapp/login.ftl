<#include "base.ftl">
<#macro title>Login</#macro>
<#macro content>
    <div id="loginForm" class="login-form">
        <h3>Login</h3>
        <form action="/login" method="post">
            <div class="form-group">
                <label for="login">Login:</label>
                <input type="text" id="login" name="login" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>
            <input type="submit" value="Enter" class="btn">
        </form>
    </div>
</#macro>
