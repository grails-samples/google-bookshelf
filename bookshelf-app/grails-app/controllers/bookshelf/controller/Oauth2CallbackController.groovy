package bookshelf.controller

import com.google.api.client.auth.oauth2.TokenResponse
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.plugins.googlecloud.authorization.GoogleAuthorizationService

import javax.servlet.http.HttpServletResponse

@Slf4j
@CompileStatic
class Oauth2CallbackController implements GrailsConfigurationAware {
    static allowedMethods = [index: 'GET']

    public static final String SESSION_ATTRIBUTE_TOKEN = 'token'
    public static final String SESSION_USER_ID = 'userId'
    public static final String SESSION_USER_EMAIL = 'userEmail'
    public static final String SESSION_USER_IMAGE_URL = 'userImageUrl'

    String defaultLoginDestination

    GoogleAuthorizationService googleAuthorizationService

    @SuppressWarnings('LineLength')
    def index(String state, String code) {

        // Ensure that this is no request forgery going on, and that the user
        // sending us this connect request is the user that was supposed to.
        if ( session[LoginController.SESSION_ATTRIBUTE_STATE] == null || !(state == session[LoginController.SESSION_ATTRIBUTE_STATE])) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            redirect(uri: defaultLoginDestination)
            return
        }

        session.removeAttribute(LoginController.SESSION_ATTRIBUTE_STATE)     // Remove one-time use state.

        def flow = googleAuthorizationService.flow()
        final TokenResponse tokenResponse = googleAuthorizationService.tokenResponse(flow, code)

        session[SESSION_ATTRIBUTE_TOKEN] = tokenResponse.toString() // Keep track of the token.

        Map<String, String> userIdResult = googleAuthorizationService.useIdResultForTokenResponse(flow, tokenResponse)

        // From this map, extract the relevant profile info and store it in the session.
        session[SESSION_USER_EMAIL] = userIdResult['email']
        session[SESSION_USER_ID] = userIdResult['sub']
        session[SESSION_USER_IMAGE_URL] = userIdResult['picture']
        def destination = session[LoginController.SESSION_ATTRIBUTE_LOGIN_DESTINATION]
        log.info "Login successful, redirecting to $destination"
        redirect(uri: destination)
    }

    @Override
    void setConfiguration(Config co) {
        defaultLoginDestination = co.getProperty('bookshelf.loginDestination', String, '/books')
    }
}
