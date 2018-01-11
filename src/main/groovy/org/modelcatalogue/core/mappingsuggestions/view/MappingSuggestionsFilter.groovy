package org.modelcatalogue.core.mappingsuggestions.view

import groovy.transform.CompileStatic

@CompileStatic
interface MappingSuggestionsFilter {
    String getTerm()
    Integer getMax()
    List<String> getStatusList()
    Integer getScore()

}
