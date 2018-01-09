package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
interface MappingSuggestion {
    Long getMappingSuggestionId()
    ElementCompared getSource()
    ElementCompared getDestination()
    MappingSuggestionStatus getStatus()
    float getScore()
}