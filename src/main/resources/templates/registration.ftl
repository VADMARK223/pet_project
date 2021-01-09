<#-- @ftlvariable name="error" type="java.lang.String" -->
<#include "base.ftl">

<#macro page_head>
    <title>Registration</title>
</#macro>

<#macro article>
    <form action="/registration" method="post">
        <@spring.bind path="userForm"/>

        <#if error??>
            <p style="color: red">${error}</p>
        </#if>

        <table>
            <tr>
                <td>Username:</td>
                <td>
                    <@spring.formInput "userForm.name" "name" "text"/>
                </td>
            </tr>
            <tr>
                <td>Password:</td>
                <td>
                    <@spring.formInput "userForm.password" "password" "password"/>
                </td>
            </tr>
            <tr>
                <td>Password confirmation:</td>
                <td>
                    <@spring.formInput "userForm.confirmPassword" "confirmPassword" "password"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Register">
                </td>
            </tr>
        </table>
    </form>
</#macro>

<@display_page/>