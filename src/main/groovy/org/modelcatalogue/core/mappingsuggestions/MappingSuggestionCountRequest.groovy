package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
interface MappingSuggestionCountRequest {
    Long getBatchId()
    List<MappingSuggestionStatus> getStatusList()
    Integer getScorePercentage()
    String getTerm()
}