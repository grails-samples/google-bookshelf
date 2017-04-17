<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h3><g:message code="book.create" default="Create book"/></h3>
    <g:render template="form" model="${[book: book, destination: 'create']}"/>
</div>
</body>
</html>