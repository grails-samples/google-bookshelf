<%@ page import="bookshelf.controller.Oauth2CallbackController" %>

<html lang="en">
<head>
    <title><g:layoutTitle default="Bookshelf - Grails on Google Cloud Platform"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <asset:stylesheet href="application.css"/>
    <g:layoutHead/>
</head>
<body>
<div class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <div class="navbar-brand"><g:message code="bookshelf" default="Bookshelf"/></div>
        </div>
        <ul class="nav navbar-nav">
            <li><g:link controller="book" action="index"><g:message code="books" default="Books"/></g:link></li>
            <g:if test="${application.isAuthConfigured}">
                <li><g:link controller="book" action="mine"><g:message code="books.mine" default="My books"/></g:link></li>
            </g:if>
        </ul>
        <bookshelfLocalizations:navbar uri="${request.forwardURI}"/>
        <p class="navbar-text navbar-right">
            <g:if test="${session[Oauth2CallbackController.SESSION_ATTRIBUTE_TOKEN]}">
                <g:link controller="logout">
                    <g:if test="${session[Oauth2CallbackController.SESSION_USER_IMAGE_URL]}">
                        <img class="img-circle" src="${session[Oauth2CallbackController.SESSION_USER_IMAGE_URL]}" width="24">
                        </g:if>
                    ${session[Oauth2CallbackController.SESSION_USER_EMAIL]}
                </g:link>
            </g:if>
            <g:elseif test="${application.isAuthConfigured}">
                <g:link controller="login"><g:message code="login" default="Login"/></g:link>
            </g:elseif>
        </p>
    </div>
</div>
<g:layoutBody/>
</body>
</html>