<#include "base.ftl">

<#macro page_head>
    <title>Admin</title>
</#macro>

<#macro article>
    <#list users as user>
        <form action="/admin/user/${user.id}" method="post">
            <h2 class="hello-title">User: №${user.id} ${user.username} <input type="submit" value="Remove"></h2>
        </form>
    </#list>
</#macro>

<@display_page/>