package bookshelf.business

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import org.grails.plugins.googlecloud.storage.CloudStorage
import org.springframework.web.multipart.MultipartFile
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@SuppressWarnings(['GrailsStatelessService', 'JavaIoPackageAccess', 'ThrowRuntimeException', 'ElseBlockBraces'])
@CompileStatic
class LocalStorageService implements CloudStorage, GrailsConfigurationAware {

    String localStorageFolder
    String localDataUrl

    @Override
    void setConfiguration(Config co) {
        localStorageFolder = co.getRequiredProperty('bookshelf.localStorageFolder')
        localDataUrl = co.getRequiredProperty('bookshelf.localDataUrl')

        def f = new File(localStorageFolder)
        if ( !f.exists() ) {
            log.error "${localStorageFolder} folder does not exist"
        } else if ( !f.isDirectory() ) {
            log.error "${localStorageFolder} is not a folder"
        }
    }

    @Override
    boolean deleteFile(String fileName) {
        def path = "${localStorageFolder}/${fileName}" as String
        log.info "deleting file: ${path}"
        new File(path).delete()
    }

    @Override
    String storeMultipartFile(String fileName, MultipartFile file) {
        def path = "${localStorageFolder}/${fileName}" as String
        log.info "saving file: ${path}"
        file.transferTo(new File(path))
        "${localDataUrl}/${fileName}"
    }

    @Override
    String storeInputStream(String fileName, InputStream inputStream) {
        null
    }

    @Override
    String storeBytes(String fileName, byte[] bytes) {
        null
    }
}
