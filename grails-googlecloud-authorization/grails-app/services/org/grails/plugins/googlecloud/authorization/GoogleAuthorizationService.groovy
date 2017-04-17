package org.grails.plugins.googlecloud.authorization

import com.fasterxml.jackson.databind.ObjectMapper

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.auth.oauth2.TokenResponse
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestFactory
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson.JacksonFactory
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import java.security.SecureRandom
import groovy.transform.CompileStatic

@SuppressWarnings('GrailsStatelessService')
@CompileStatic
class GoogleAuthorizationService implements GrailsConfigurationAware {
    private static final String USERINFO_ENDPOINT = 'https://www.googleapis.com/plus/v1/people/me/openIdConnect'

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport()
    private static final Collection<String> SCOPES = ['email', 'profile']
    private static final JsonFactory JSON_FACTORY = new JacksonFactory()

    String clientID
    String clientSecret
    String callback

    static String randomState() {
        new BigInteger(130, new SecureRandom()).toString(32)
    }

    /**
     *
     * @param state - Used to prevent request forgery
     * @return
     */
    String authorizationRedirectUrl(String state) {
        def flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                clientID,
                clientSecret,
                SCOPES)
                .build()
        flow.newAuthorizationUrl()
                .setRedirectUri(callback)
                .setState(state)            // Prevent request forgery
                .build()
    }

    GoogleAuthorizationCodeFlow flow() {
        new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                clientID,
                clientSecret,
                SCOPES).build()
    }

    TokenResponse tokenResponse(GoogleAuthorizationCodeFlow flow, String authorizationCode) {
        flow.newTokenRequest(authorizationCode)
                .setRedirectUri(callback)
                .execute()
    }

    Map<String, String> useIdResultForTokenResponse(GoogleAuthorizationCodeFlow flow, TokenResponse tokenResponse) {
        final Credential credential = flow.createAndStoreCredential(tokenResponse, null)
        final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential)
        final GenericUrl url = new GenericUrl(USERINFO_ENDPOINT)      // Make an authenticated request.
        final HttpRequest request = requestFactory.buildGetRequest(url)
        request.headers.contentType = 'application/json'
        final String jsonIdentity = request.execute().parseAsString()
        new ObjectMapper().readValue(jsonIdentity, HashMap)
    }

    @Override
    void setConfiguration(Config co) {
        clientID = co.getRequiredProperty('bookshelf.clientID', String)
        clientSecret = co.getRequiredProperty('bookshelf.clientSecret', String)
        callback = co.getRequiredProperty('bookshelf.callback', String)
    }
}
