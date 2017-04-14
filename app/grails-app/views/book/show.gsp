<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h3>Book</h3>
    <div class="btn-group">
        <a href="/update/${book.id}" class="btn btn-primary btn-sm">
            <i class="glyphicon glyphicon-edit"></i>
            Edit book
        </a>
        <a href="/delete/${book.id}" class="btn btn-danger btn-sm">
            <i class="glyphicon glyphicon-trash"></i>
            Delete book
        </a>
    </div>

    <div class="media">
        <div class="media-left">
            <img class="book-image" src="${book.imageUrl ?: 'http://placekitten.com/g/128/192'}">
        </div>
        <div class="media-body">
            <h4 class="book-title">
                ${book.title}
                <small>${book.publishedDate}</small>
            </h4>
            <h5 class="book-author">By ${book.author ?: 'Unknown'}</h5>
            <p class="book-description">${book.description}</p>
            <small class="book-added-by">Added by ${book.createdBy ?: 'Anonymous'}</small>
        </div>
    </div>
</div>
</body>
</html>