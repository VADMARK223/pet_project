<#include "base.ftl">
<#-- @ftlvariable name="user" type="ru.vadmark.petproject2.model.User" -->

<#macro page_head>
    <title>Index</title>
</#macro>

<#macro page_body>
    <#if user??>
        Hello, ${user.username}.
    <#else>
        <form action="/login" method="post">
            <#--<#if error??>
                <p style="color: red">${error}</p>
            </#if>-->

            <table>
                <tr>
                    <td>Username:</td>
                    <td>
                        <label>
                            <input type="text" name="username">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td>
                        <label>
                            <input type="password" name="password">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Sing in">
                    </td>
                </tr>
            </table>
        </form>
    </#if>
</#macro>

<@display_page/>


