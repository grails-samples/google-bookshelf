package org.grails.plugins.googlecloud.vision

import com.google.cloud.vision.spi.v1.ImageAnnotatorClient
import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.AnnotateImageResponse
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Feature.Type
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.TextAnnotation
import com.google.protobuf.ByteString
import groovy.util.logging.Slf4j
import groovy.transform.CompileStatic

@Slf4j
@CompileStatic
class GoogleCloudVisionService {

    @SuppressWarnings(['ReturnNullFromCatchBlock', 'CatchException'])
    String detectDocumentText(InputStream inputStream) {
        try {
            List<AnnotateImageRequest> requests = []

            ByteString imgBytes = ByteString.readFrom(inputStream)

            Image img = Image.newBuilder().setContent(imgBytes).build()
            Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build()
            AnnotateImageRequest request =
                    AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build()
            requests.add(request)

            BatchAnnotateImagesResponse response =
                    ImageAnnotatorClient.create().batchAnnotateImages(requests)
            List<AnnotateImageResponse> responses = response.responsesList

            List<String> responsesText = []
            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    log.error "Error ${res.error.message}"
                    continue
                }

                TextAnnotation annotation = res.fullTextAnnotation
                responsesText << annotation.text
            }

            return responsesText*.replaceAll('\n', ' ').join(' ').trim().replaceAll(' +', ' ')

        } catch ( Exception e ) {
            log.error(e.message, e)
            return null
        }
    }
}
