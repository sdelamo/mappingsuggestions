package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
interface MappingSuggestionRequest {
    Long getBatchId()
    List<MappingSuggestionStatus> getStatusList()
    Integer getMax()
    Integer getOffset()
}