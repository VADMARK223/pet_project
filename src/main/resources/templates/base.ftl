<#import "/spring.ftl" as spring />
<#macro page_head>
    <title>Base</title>
</#macro>

<#macro page_body>
    Body
</#macro>

<#macro display_page>
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <@page_head/>
            <link rel="stylesheet" href="/css/bootstrap.min.css">
            <link rel="stylesheet" href="/css/main.css">
            <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
        </head>
        <body>
            <h2>Freemarker</h2>
            <div id="nav"><a href="/">Home</a></div>
            <@page_body/>
        </body>
    </html>
</#macro>