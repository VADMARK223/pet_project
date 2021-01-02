<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#include "base.ftl">
<#--<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>-->

<#macro page_head>
    <title>Index</title>
</#macro>

<#macro page_body>
    <@sec.authorize access="!isAuthenticated()">
        <a href="/login">Sing in</a><br>
    </@sec.authorize>
    <@sec.authorize access="isAuthenticated()">
        <a href="/logout">Log out</a><br>
    </@sec.authorize>
    <a href="/registration">Registration</a><br>
    <a href="/users">Users</a><br>
    <a href="/upload">Upload</a><br>
</#macro>

<@display_page/>