<#--noinspection HtmlRequiredTitleElement-->
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
        <script src="js/bootstrap.min.js"></script>
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
    </head>
    <body>

    <div class="modal fade show" tabindex="-1" id="recordModal" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Add weight</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p>Empty body</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save</button>
                </div>
            </div>
        </div>
    </div>

    <header class="navbar navbar-expand-md navbar-dark bg-dark py-0">
        <nav class="container-xxl flex-wrap flex-md-nowrap" aria-label="Main navigation">
            <a href="/" class="navbar-brand">
                <img src="/images/freemarker.png" alt="Freemarker">Freemarker
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#dbNavbar"
                    aria-controls="dbNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="dbNavbar">
                <ul class="navbar-nav">
                    <@sec.authorize access="!isAuthenticated()">
                        <a class="nav-link" href="/login">Sing in</a>
                        <a class="nav-link" href="/registration">Registration</a>
                    </@sec.authorize>

                    <@sec.authorize access="isAuthenticated()">
                        <a class="nav-link" href="/logout">Log out</a>

                        <@sec.authorize access="hasRole('ADMIN')">
                            <a class="nav-link" href="/admin">Admin</a>
                        </@sec.authorize>

                        <a class="nav-link" href="/settings">Settings</a>
                    </@sec.authorize>
                </ul>
                <ul class="navbar-nav ms-md-auto">
                    <li class="nav-item nav-link">
                        <@sec.authorize access="isAuthenticated()">
                            Welcome back, <@sec.authentication property="principal.username"/>.
                        </@sec.authorize>
                    </li>
                    <li class="nav-item">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                data-bs-target="#recordModal">
                            <span aria-hidden="true">&plus;</span>Add weight
                        </button>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <article>
        <@article/>
    </article>

    </body>
    </html>
</#macro>