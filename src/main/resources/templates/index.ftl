<#include "base.ftl">
<#--<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>-->

<#macro page_head>
    <title>Index</title>
</#macro>

<#macro page_body>
    <a href="/login">Sing in</a><br>
    <a href="/registration">Registration</a><br>
    <a href="/users">Users</a><br>
    <a href="/logout">Log out</a><br>
</#macro>

<@display_page/>