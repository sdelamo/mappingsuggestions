package org.modelcatalogue.core.mappingsuggestions

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import spock.lang.Unroll
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK

class MappingSuggestionsControllerAllowedMethodsSpec extends Specification
        implements ControllerUnitTest<MappingSuggestionsController> {

    @Unroll
    def "test MappingSuggestionController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test MappingSuggestionController.index accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.index()

        then:
        response.status == SC_OK
    }

    @Unroll
    def "test MappingSuggestionController.reject does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.reject()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test MappingSuggestionController.reject accepts POST requests"() {
        when:
        request.method = 'POST'
        controller.reject()

        then:
        response.status == SC_OK
    }

    @Unroll
    def "test MappingSuggestionController.approve does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.approve()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test MappingSuggestionController.approve accepts POST requests"() {
        when:
        request.method = 'POST'
        controller.approve()

        then:
        response.status == SC_OK
    }

    @Unroll
    def "test MappingSuggestionController.approveAll does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.approveAll()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test MappingSuggestionController.approveAll accepts POST requests"() {
        when:
        request.method = 'POST'
        controller.approveAll()

        then:
        response.status == SC_OK
    }

}
