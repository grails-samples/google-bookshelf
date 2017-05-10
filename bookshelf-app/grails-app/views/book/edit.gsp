<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h3><g:message code="book.edit" default="Edit book"/></h3>
    <g:render template="form" model="${[book: book, destination: 'update']}"/>
</div>
</body>
</html>