package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
class MappingSuggestionRequestImpl implements MappingSuggestionRequest, MappingSuggestionCountRequest {
    Long batchId
    List<MappingSuggestionStatus> statusList
    Integer max
    Integer offset
    Integer scorePercentage
    String term
}
