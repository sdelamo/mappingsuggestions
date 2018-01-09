package org.modelcatalogue.core.mappingsuggestions

import grails.testing.web.UrlMappingsUnitTest
import org.modelcatalogue.core.UrlMappings
import spock.lang.Specification

class MappingSuggestionsUrlMappingsSpec extends Specification implements UrlMappingsUnitTest<MappingSuggestionsUrlMappings> {

    void setup() {
        mockController(MappingSuggestionsController)
    }

    void "test MappingSuggestions mappings"() {
        expect:
        verifyForwardUrlMapping("/mappingsuggestions", controller: 'mappingSuggestions')
        verifyForwardUrlMapping("/mappingsuggestions/reject", controller: 'mappingSuggestions', action: 'reject')
        verifyForwardUrlMapping("/mappingsuggestions/approve", controller: 'mappingSuggestions', action: 'approve')
        verifyForwardUrlMapping("/mappingsuggestions/approveAll", controller: 'mappingSuggestions', action: 'approveAll')
    }
}
