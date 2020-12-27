<#include "base.ftl">

<#macro page_head>
    <title>Users</title>
</#macro>

<#macro page_body>
    <#list users as user>
        <h2 class="hello-title">User: ${user.username}.</h2>
    </#list>
</#macro>

<@display_page/>