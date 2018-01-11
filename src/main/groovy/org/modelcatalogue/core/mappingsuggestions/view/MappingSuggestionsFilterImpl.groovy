package org.modelcatalogue.core.mappingsuggestions.view

import groovy.transform.CompileStatic

@CompileStatic
class MappingSuggestionsFilterImpl implements MappingSuggestionsFilter {
    String term
    Integer max
    List<String> statusList
    Integer score
}
