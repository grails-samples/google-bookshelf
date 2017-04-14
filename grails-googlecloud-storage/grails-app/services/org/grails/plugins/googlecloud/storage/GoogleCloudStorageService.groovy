package org.grails.plugins.googlecloud.storage

import com.google.cloud.storage.Acl
import com.google.cloud.storage.BlobId
import groovy.transform.CompileStatic


import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import org.springframework.web.multipart.MultipartFile

@CompileStatic
class GoogleCloudStorageService implements GrailsConfigurationAware {
    // Cloud Storage Bucket
    String bucket

    Storage storage = StorageOptions.getDefaultInstance().getService()

    String storeMultipartFile(String fileName, MultipartFile multipartFile) {
        storeInputStream(fileName, multipartFile.inputStream)
    }

    String storeInputStream(String fileName, InputStream inputStream) {
        BlobInfo blobInfo = storage.create(readableBlobInfo(bucket, fileName), inputStream)
        blobInfo.mediaLink
    }

    String storeBytes(String fileName, byte[] bytes) {
        BlobInfo blobInfo = storage.create(readableBlobInfo(bucket, fileName), bytes)
        blobInfo.mediaLink
    }

    private static BlobInfo readableBlobInfo(String bucket, String fileName) {
        BlobInfo.newBuilder(bucket, fileName)
                .setAcl([Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)]) // Modify access list to allow all users with link to read file
                .build()
    }

    boolean deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucket, fileName)
        storage.delete(blobId)
    }

    @Override
    void setConfiguration(Config co) {
        bucket = co.getRequiredProperty('org.grails.plugins.googlecloud.storage.bucket', String)
    }
}
