<#include "base.ftl">
<#-- @ftlvariable name="user" type="ru.vadmark.petproject.entity.UserEntity" -->

<#macro page_head>
    <title>Index</title>

    <#--<script>
        window.addEventListener("load", function () {
            const loginForm = document.getElementById("login-form");
            loginForm.addEventListener("submit", submitHandler);

            function submitHandler(event) {
                event.preventDefault();

                const userInputValue = document.getElementById("username").value;
                const passwordInputValue = document.getElementById("password").value;
                const params = {"username": userInputValue, "password": passwordInputValue};
                // const config = {
                //     headers: {
                //         'test': 'aaaaaaaaaaaaaa'
                //     }
                // }
                axiosAPI.post('/login', params/*, config*/).then(value => {
                    window.location.href = "/";
                });
            }
        });
    </script>-->
</#macro>

<#macro article>
    <#if user??>
        Hello, ${user.username}.
    <#else>
            <form action="/login" method="post">
<#--        <form id="login-form">-->
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


