package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
interface GenerateMappingSuggestionsRequest {
    Long getSourceId()
    Long getDestinationId()
}