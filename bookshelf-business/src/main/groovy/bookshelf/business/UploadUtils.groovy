package bookshelf.business

import groovy.transform.CompileStatic
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.web.multipart.MultipartFile

@CompileStatic
class UploadUtils {
    static String nameForFile(MultipartFile file) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern('YYYY-MM-dd-HHmmssSSS')
        DateTime dt = DateTime.now(DateTimeZone.UTC)
        String dtString = dt.toString(dtf)
        String fileName = file.originalFilename
        fileName = fileName.replaceAll(' ', '_')
        fileName = fileName.toLowerCase()
        "${dtString}-${fileName}"
    }
}
