package org.grails.plugins.googlecloud.storage

import groovy.transform.CompileStatic
import org.springframework.web.multipart.MultipartFile

@CompileStatic
interface CloudStorage {
    boolean deleteFile(String fileName)
    String storeMultipartFile(String fileName, MultipartFile multipartFile)
    String storeInputStream(String fileName, InputStream inputStream)
    String storeBytes(String fileName, byte[] bytes)
}
