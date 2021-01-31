<#-- @ftlvariable name="users" type="java.util.List<ru.vadmark.petproject.entity.UserEntity>" -->
<#include "base.ftl">

<#macro page_head>
    <title>Admin</title>
</#macro>

<#macro article>
    <table>
        <tr>
            <th>№</th>
            <th>Username</th>
            <th>Remove</th>
        </tr>
        <#list users as user>
            <form action="/admin/user/${user.id}" method="post">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td><input type="submit" value="Remove"></td>
                </tr>
            </form>
        </#list>

    </table>
</#macro>

<@display_page/>