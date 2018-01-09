package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
class GenerateMappingSuggestionsRequestImpl implements GenerateMappingSuggestionsRequest {
    Long sourceId
    Long destinationId
}
