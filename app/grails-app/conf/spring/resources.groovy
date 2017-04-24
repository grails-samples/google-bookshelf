import com.example.getstarted.domain.BookDescriptionListener

// Place your Spring DSL code here
beans = {
    bookDescriptionListener(BookDescriptionListener, ref('hibernateDatastore'))
}