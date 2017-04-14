package com.example.getstarted

import groovy.transform.CompileStatic
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.web.multipart.MultipartFile

@CompileStatic
class UploadBookCoverService {

    String nameForFile(MultipartFile file) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS")
        DateTime dt = DateTime.now(DateTimeZone.UTC)
        String dtString = dt.toString(dtf)
        "${file.originalFilename}dtString"
    }

}