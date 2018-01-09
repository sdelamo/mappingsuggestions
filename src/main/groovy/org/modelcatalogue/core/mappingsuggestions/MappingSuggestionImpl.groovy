package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
class MappingSuggestionImpl implements MappingSuggestion {
    Long mappingSuggestionId
    ElementCompared source
    ElementCompared destination
    MappingSuggestionStatus status
    float score
}
