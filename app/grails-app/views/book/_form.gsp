<g:form method="POST" url="/${destination}${book?.id ? ('/' + book.id) : ''}" enctype="multipart/form-data">

    <div class="form-group">
        <label for="title">Title</label>
        <input type="text" name="title" id="title" value="${book?.title}" class="form-control" />
    </div>

    <div class="form-group">
        <label for="author">Author</label>
        <input type="text" name="author" id="author" value="${book?.author}" class="form-control" />
    </div>

    <div class="form-group">
        <label for="publishedDate">Date Published</label>
        <input type="text" name="publishedDate" id="publishedDate" value="${book?.publishedDate}" class="form-control" />
    </div>

    <div class="form-group">
        <label for="description">Description</label>
        <textarea name="description" id="description" class="form-control">${book?.description}</textarea>
    </div>

    <div class="form-group ${isCloudStorageConfigured ? '' : 'hidden'}">
        <label for="file">Cover Image</label>
        <input type="file" name="file" id="file" class="form-control" />
    </div>

    <div class="form-group hidden">
        <label for="imageUrl">Cover Image URL</label>
        <input type="hidden" name="id" value="${book?.id}" />
        <input type="text" name="imageUrl" id="imageUrl" value="${book?.imageUrl}" class="form-control" />
    </div>

    <button type="submit" class="btn btn-success">Save</button>
</g:form>