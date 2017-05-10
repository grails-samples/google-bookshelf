package bookshelf.business

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic
import org.grails.plugins.googlecloud.storage.CloudStorage
import org.grails.plugins.googlecloud.storage.GoogleCloudStorageService
import org.springframework.web.multipart.MultipartFile

@SuppressWarnings('GrailsStatelessService')
@CompileStatic
class CloudStorageService implements CloudStorage, GrailsConfigurationAware {

    GoogleCloudStorageService googleCloudStorageService

    LocalStorageService localStorageService

    String storageType

    @Override
    void setConfiguration(Config co) {
        storageType = co.getProperty('bookshelf.storageType', String, 'cloudStorage')
    }

    CloudStorage getCloudStorage() {

        if ( storageType == 'localStorage' ) {
            return localStorageService
        }

        if ( storageType == 'cloudStorage' ) {
            return googleCloudStorageService
        }

        googleCloudStorageService
    }

    @Override
    boolean deleteFile(String fileName) {
        cloudStorage.deleteFile(fileName)
    }

    @Override
    String storeMultipartFile(String fileName, MultipartFile multipartFile) {
        cloudStorage.storeMultipartFile(fileName, multipartFile)
    }

    @Override
    String storeInputStream(String fileName, InputStream inputStream) {
        cloudStorage.storeInputStream(fileName, inputStream)
    }

    @Override
    String storeBytes(String fileName, byte[] bytes) {
        cloudStorage.storeBytes(fileName, bytes)
    }
}
