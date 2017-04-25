<g:form method="POST" url="/${destination}${book?.id ? ('/' + book.id) : ''}" enctype="multipart/form-data">

    <div class="form-group">
        <label for="title"><g:message code="book.title" default="Title"/></label>
        <input type="text" name="title" id="title" value="${book?.title}" class="form-control" />
    </div>

    <div class="form-group">
        <label for="author"><g:message code="book.author" default="Author"/></label>
        <input type="text" name="author" id="author" value="${book?.author}" class="form-control" />
    </div>

    <div class="form-group">
        <label for="publishedDate"><g:message code="book.date.published" default="Date Published"/></label>
        <input type="text" name="publishedDate" id="publishedDate" value="${book?.publishedDate}" class="form-control" />
    </div>

    <div class="form-group">
        <label for="description"><g:message code="book.description" default="Description"/></label>
        <textarea name="description" id="description" class="form-control">${book?.description}</textarea>
    </div>

    <div class="form-group ${application.isCloudStorageConfigured ? '' : 'hidden'}">
        <label for="file"><g:message code="book.cover.image" default="Cover Image"/></label>
        <input type="file" name="file" id="file" class="form-control" />
    </div>

    <div class="form-group hidden">
        <label for="imageUrl"><g:message code="book.cover.image.url" default="Cover Image URL"/></label>
        <input type="hidden" name="id" value="${book?.id}" />
        <input type="text" name="imageUrl" id="imageUrl" value="${book?.imageUrl}" class="form-control" />
        <input type="text" name="createdById" id="createdById" value="${book?.createdById}" class="form-control" />
        <input type="text" name="createdBy" id="createdBy" value="${book?.createdBy}" class="form-control" />
    </div>
    <button type="submit" class="btn btn-success"><g:message code="book.save" default="Save"/></button>
</g:form>