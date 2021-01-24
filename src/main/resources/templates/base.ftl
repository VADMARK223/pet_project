<#import "/spring.ftl" as spring />
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#macro page_head>
    <title>Base</title>
</#macro>

<#macro article>
    <p style="color: red">WARN: Implement article macro.</p>
</#macro>

<#macro display_page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <@page_head/>
        <link rel="stylesheet" href="/css/bootstrap.min.css">
        <link rel="stylesheet" href="/css/freemarker.css">
        <link rel="shortcut icon" href="/images/freemarker.ico" type="image/x-icon">
        <script src="/js/axios.min.js"></script>
        <script>
            const jwt = localStorage.getItem("BEARER_JWT");
            // console.log("jwt: " + jwt);
            const headers = {
                'Content-Type': 'application/json'
                // 'Authorization': jwt
            }
            const axiosAPI = axios.create({
                headers
            });
        </script>

        <#--<script>
            const jwt = localStorage.getItem("BEARER_JWT");
            // console.log("jwt: " + jwt);
            const headers = {
                'Content-Type': 'application/json'
                // 'Authorization': jwt
            }
            const axiosAPI = axios.create({
                headers
            });
            window.addEventListener("load", function () {
                console.log("All resources loading!");
                axiosAPI.post('/auth').then(res => {
                    console.log("Res: " + res.data);
                    console.log("Header Authorization: " + res.headers.authorization)
                    //localStorage.setItem("BEARER_JWT", res.data);
                })
                    .catch(function (error) {
                        console.log("Error: " + error)
                        // localStorage.setItem("TEST", "VADMARK");
                        // window.location.href = "/login";
                    });
            })
        </script>-->
    </head>
    <body>
    <header>
        <h2><a href="/">Freemarker</a></h2>
        <@sec.authorize access="isAuthenticated()">
            <h4>Welcome back, <@sec.authentication property="name"/>.</h4>
        </@sec.authorize>
    </header>

    <nav class="base-nav">
        <@sec.authorize access="!isAuthenticated()">
            <a href="/login">Sing in</a><br>
            <a href="/registration">Registration</a><br>
        </@sec.authorize>
        <@sec.authorize access="isAuthenticated()">
            <a href="/logout">Log out</a><br>
            <@sec.authorize access="hasRole('ADMIN')">
                <a href="/admin">Admin</a><br>
            </@sec.authorize>
            <a href="/settings">Settings</a><br>
        </@sec.authorize>
    </nav>
    <article class="base-article">
        <@article/>
    </article>

    </body>
    </html>
</#macro>