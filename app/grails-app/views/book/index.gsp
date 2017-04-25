<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h3>${title}</h3>
    <a href="/create">
        <i class="glyphicon glyphicon-plus"></i>
        <g:message code="book.add" default="Add book"/>
    </a>
    <g:if test="${books}">
        <g:each in="${books}" var="book">
            <div class="media">
                <g:link controller="book" action="show" id="${book.id}">
                    <div class="media-left">
                        <img alt="ahhh" src="${book?.imageUrl ?: 'http://placekitten.com/g/128/192'}">
                    </div>
                    <div class="media-body">
                        <h4><bookshelf:bookTitle id="${book?.id}" languageCode="${RequestContextUtils.getLocale(request).language}"/></h4>
                        <p>${book.author}</p>
                    </div>
                </g:link>
            </div>
        </g:each>
        <g:if test="${cursor}">
            <nav>
                <ul class="pager">
                    <li><a href="?cursor=${cursor}"><g:message code="book.list.more" default="More"/></a></li>
                </ul>
            </nav>
        </g:if>
    </g:if>
    <g:else>
        <p><g:message code="book.list.notFound" default="No books found"/></p>
    </g:else>
</div>
</body>
</html>