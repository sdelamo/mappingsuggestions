package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
class MappingSuggestionStatusUtils {

    static List<String> possibleValues() {
        MappingSuggestionStatus.values().collect {
            MappingSuggestionStatus status -> status.name()
        } as List<String>
    }

    static MappingSuggestionStatus of(String status) {
        if ( status == 'PENDING' ) {
            return MappingSuggestionStatus.PENDING
        }
        if ( status == 'APPROVED' ) {
            return MappingSuggestionStatus.APPROVED
        }
        if ( status == 'REJECTED' ) {
            return MappingSuggestionStatus.REJECTED
        }
    }
}
