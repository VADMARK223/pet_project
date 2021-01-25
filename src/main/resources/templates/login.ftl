<#include "base.ftl">
<#-- @ftlvariable name="user" type="ru.vadmark.petproject.entity.UserEntity" -->

<#macro page_head>
    <title>Index</title>
</#macro>

<#macro article>
    <#if user??>
        Hello, ${user.username}.
    <#else>
            <form action="/svelte/login" method="post">
            <#if error??>
                <p style="color: red">${error}</p>
            </#if>

            <table>
                <tr>
                    <td>Username:</td>
                    <td>
                        <label>
                            <input id="username" type="text" name="username">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td>
                        <label>
                            <input id="password" type="password" name="password">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Sign in">
                    </td>
                </tr>
            </table>
        </form>
    </#if>
</#macro>

<@display_page/>


