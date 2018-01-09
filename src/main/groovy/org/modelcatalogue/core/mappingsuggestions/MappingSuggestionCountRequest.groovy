package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
interface MappingSuggestionCountRequest {
    Long getBatchId()
    List<String> getStatusList()
}