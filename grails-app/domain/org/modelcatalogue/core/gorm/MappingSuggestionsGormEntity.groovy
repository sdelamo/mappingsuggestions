package org.modelcatalogue.core.gorm

import grails.compiler.GrailsCompileStatic
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionStatus
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionType

@GrailsCompileStatic
class MappingSuggestionsGormEntity {
    Long sourceId
    MappingSuggestionType sourceType
    String sourceName
    String sourceCode
    Long destinationId
    MappingSuggestionType destinationType
    String destinationName
    String destinationCode
    float result
    MappingSuggestionStatus status

    static belongsTo = [batch: DataModelMappingSuggestions]
}