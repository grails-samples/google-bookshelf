package org.grails.plugins.googlecloud.storage

import groovy.transform.CompileStatic

import com.google.cloud.storage.Acl
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.util.logging.Slf4j
import org.springframework.web.multipart.MultipartFile

@Slf4j
@SuppressWarnings('GrailsStatelessService')
@CompileStatic
class GoogleCloudStorageService implements GrailsConfigurationAware {
    // Cloud Storage Bucket
    String bucket

    Storage storage = StorageOptions.defaultInstance.service

    String storeMultipartFile(String fileName, MultipartFile multipartFile) {
        log.info 'Uploaded file {0}', multipartFile.originalFilename
        storeInputStream(fileName, multipartFile.inputStream)
    }

    String storeInputStream(String fileName, InputStream inputStream) {
        BlobInfo blobInfo = storage.create(readableBlobInfo(bucket, fileName), inputStream)
        log.info 'Uploaded file as {0} with mediaLink {1}', fileName, blobInfo.mediaLink

        blobInfo.mediaLink
    }

    String storeBytes(String fileName, byte[] bytes) {
        BlobInfo blobInfo = storage.create(readableBlobInfo(bucket, fileName), bytes)
        blobInfo.mediaLink
    }

    private static BlobInfo readableBlobInfo(String bucket, String fileName) {
        // Modify access list to allow all users with link to read file
        List<Acl> acl = [Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)]
        BlobInfo.newBuilder(bucket, fileName)
                .setAcl(acl)
                .build()
    }

    boolean deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucket, fileName)
        storage.delete(blobId)
    }

    @Override
    void setConfiguration(Config co) {
        bucket = co.getProperty('org.grails.plugins.googlecloud.storage.bucket', String)
    }
}
