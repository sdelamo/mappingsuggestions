package org.modelcatalogue.core.mappingsuggestions

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class MappingSuggestionIndexCommand implements Validateable {
    Long batchId
    Integer max
    Integer offset
    List<String> status

    static constraints = {
        batchId nullable: false
        max nullable: true, min: 1
        offset nullable: true, min: 0
        status nullable: true, validator: { List<String> val, MappingSuggestionIndexCommand obj ->
            List<String> validStatusList = MappingSuggestionStatusUtils.possibleValues()
            val == null || val.every { validStatusList.contains(it) }
        }
    }
}