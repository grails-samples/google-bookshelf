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
class GoogleCloudStorageService implements GrailsConfigurationAware, CloudStorage {
    // Cloud Storage Bucket
    String bucket

    Storage storage = StorageOptions.defaultInstance.service

    @Override
    void setConfiguration(Config co) {
        bucket = co.getProperty('org.grails.plugins.googlecloud.storage.bucket', String)
    }

    @Override
    String storeMultipartFile(String fileName, MultipartFile multipartFile) {
        log.info "Uploaded file ${multipartFile.originalFilename}"
        storeInputStream(fileName, multipartFile.inputStream)
    }

    @Override
    String storeInputStream(String fileName, InputStream inputStream) {
        BlobInfo blobInfo = storage.create(readableBlobInfo(bucket, fileName), inputStream)
        log.info "Uploaded file as ${fileName} with mediaLink ${blobInfo.mediaLink}"

        blobInfo.mediaLink
    }

    @Override
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

    @Override
    boolean deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucket, fileName)
        storage.delete(blobId)
    }
}
