<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h3><g:message code="book" default="Book"/></h3>
    <div class="btn-group">
        <a href="/update/${book.id}" class="btn btn-primary btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            <g:message code="book.edit" default="Edit book"/>
        </a>
        <a href="/delete/${book.id}" class="btn btn-danger btn-sm">
            <i class="glyphicon glyphicon-trash"></i>
            <g:message code="book.delete" default="Delete book"/>
        </a>
    </div>

    <div class="media">
        <div class="media-left">
            <img class="book-image" src="${book.imageUrl ?: 'http://placekitten.com/g/128/192'}">
        </div>
        <div class="media-body">
            <h4 class="book-title">${book.title}<small>${book.publishedDate}</small></h4>
            <h5 class="book-author">
                <g:if test="${book.author}">
                    <g:message code="book.author.by" args="${book.author}"/>
                </g:if>
                <g:else>
                    <g:message code="book.author.unknown" default="By Unknown"/>
                </g:else>
            </h5>
            <p class="book-description">${book.description}</p>
            <small class="book-added-by">
                <g:if test="${book.createdBy}">
                    <g:message code="book.added.by" args="${book.createdBy}"/>
                </g:if>
                <g:else>
                    <g:message code="book.added.by.anonymous" default="Added by Anonymous"/>
                </g:else>
            </small>
        </div>
    </div>
</div>
</body>
</html>