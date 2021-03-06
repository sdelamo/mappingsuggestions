package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
interface MappingsSuggestionsGateway {
    MappingSuggestionResponse findAll(MappingSuggestionRequest mappingSuggestionRequest)
    Integer count(MappingSuggestionCountRequest mappingSuggestionCountRequest)
    void reject(List<Long> actionIds)
    void approve(List<Long> actionIds)
    void approveAll(Long batchId)
    void generateMappingSuggestions(GenerateMappingSuggestionsRequest request)
}