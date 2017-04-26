<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h3><g:message code="book.create" default="Create book"/></h3>
    <g:form method="POST" url="/create${book?.id ? ('/' + book.id) : ''}" enctype="multipart/form-data">
        <div class="form-group ${application.isCloudStorageConfigured ? '' : 'hidden'}">
            <label for="file"><g:message code="book.cover.image" default="Cover Image"/></label>
            <input type="file" name="file" id="file" class="form-control" />
        </div>
        <button type="submit" class="btn btn-success"><g:message code="book.save" default="Save"/></button>

    </g:form>
</div>
</body>
</html>