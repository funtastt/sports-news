<#include "base.ftl">
<#macro title>Exception details</#macro>
<#macro content>
    <h3>Exception details</h3>

    <strong>Request uri: </strong> ${uri} <br>
    <strong>Status code: </strong> ${statusCode} <br>
    <#if message??> <strong>Message: </strong> ${message} <br> </#if>
</#macro>
