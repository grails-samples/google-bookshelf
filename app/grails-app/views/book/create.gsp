<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h3>Create book</h3>
    <g:render template="form" model="${[book: book, destination: 'create', isCloudStorageConfigured: true]}"/>
</div>
</body>
</html>