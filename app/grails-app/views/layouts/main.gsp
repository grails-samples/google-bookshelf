<html lang="en">
<head>
    <title><g:layoutTitle default="Bookshelf - Java on Google Cloud Platform"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <g:layoutHead/>
</head>
<body>
<div class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <div class="navbar-brand">Bookshelf</div>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/">Books</a></li>
            <g:if test="${isAuthConfigured}"><li><a href="/books/mine">My Books</a></li></g:if>
        </ul>
        <p class="navbar-text navbar-right">
        </p>
    </div>
</div>
<g:layoutBody/>
</body>
</html>