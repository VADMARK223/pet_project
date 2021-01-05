<#-- @ftlvariable name="imgAsBase64" type="java.lang.String" -->
<#include "base.ftl">

<#macro page_head>
    <title>Upload</title>
</#macro>

<#macro page_body>
    <img src="${imgAsBase64}" alt="Image not valid."/>

    <form action="/upload" method="post" enctype="multipart/form-data">
        <@spring.bind path="avatarForm"/>
        <@spring.formInput "avatarForm.image" "image" "file"/>
        <input type="submit" value="Upload image">
    </form>
</#macro>

<@display_page/>