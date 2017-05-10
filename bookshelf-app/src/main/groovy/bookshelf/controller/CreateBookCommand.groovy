package bookshelf.controller

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

import org.springframework.web.multipart.MultipartFile

@GrailsCompileStatic
class CreateBookCommand implements Validateable {
    MultipartFile file

    static constraints = {
        file nullable: false
    }
}
