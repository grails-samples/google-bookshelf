<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h3>Edit book</h3>
    <g:render template="form" model="${[book: book, destination: 'update', isCloudStorageConfigured: false]}"/>
</div>
</body>
</html>