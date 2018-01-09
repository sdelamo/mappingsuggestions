package org.modelcatalogue.core.mappingsuggestions

class MappingSuggestionsUrlMappings {

    static mappings = {
        "/mappingsuggestions"(controller: 'mappingSuggestions')
        "/mappingsuggestions/reject"(controller: 'mappingSuggestions', action: 'reject')
        "/mappingsuggestions/approve"(controller: 'mappingSuggestions', action: 'approve')
        "/mappingsuggestions/approveAll"(controller: 'mappingSuggestions', action: 'approveAll')
    }
}
