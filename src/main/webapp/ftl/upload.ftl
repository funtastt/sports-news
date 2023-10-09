<#include "base.ftl">
<#macro title>Upload</#macro>
<#macro content>
    <p>Upload file:</p>

    <form action="/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="fileUpload"> <br>

        <input type="submit" value="Upload" class="btn-primary btn">
    </form>
</#macro>
