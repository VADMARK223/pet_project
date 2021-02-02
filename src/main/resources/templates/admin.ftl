<#-- @ftlvariable name="users" type="java.util.List<ru.vadmark.petproject.entity.UserEntity>" -->
<#include "base.ftl">

<#macro page_head>
    <title>Admin</title>
</#macro>

<#macro article>
    <table class="table table-dark table-striped">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Username</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>

        <#list users as user>
            <form action="/admin/user/${user.id}" method="post">
                <tr>
                    <td>
                        ${user.id}
                    </td>
                    <td>
                        ${user.username}
                    </td>
                    <td>
                        <#if user.username != username>
                            <input type="submit" value="Remove">
                        </#if>
                    </td>
                </tr>
            </form>
        </#list>
        </tbody>
    </table>
</#macro>

<@display_page/>