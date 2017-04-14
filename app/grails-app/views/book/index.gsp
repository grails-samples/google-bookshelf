<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h3>Books</h3>
    <a href="/create">
        <i class="glyphicon glyphicon-plus"></i>
        Add book
    </a>
    <g:if test="${books}">
        <g:each in="${books}" var="book">
            <div class="media">
                <a href="/read?id=${book.id}">
                    <div class="media-left">
                        <img alt="ahhh" src="${book?.imageUrl ?: 'http://placekitten.com/g/128/192'}">
                    </div>
                    <div class="media-body">
                        <h4>${book.title}</h4>
                        <p>${book.author}</p>
                    </div>
                </a>
            </div>
        </g:each>
        <g:if test="${cursor}">
            <nav>
                <ul class="pager">
                    <li><a href="?cursor=${cursor}">More</a></li>
                </ul>
            </nav>
        </g:if>
    </g:if>
    <g:else>
        <p>No books found</p>

    </g:else>
</div>
</body>
</html>