package org.modelcatalogue.core.mappingsuggestions

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class MappingSuggestionIndexCommand implements Validateable {
    Long batchId
    Integer max
    Integer offset
    List<String> status
    Integer score
    String term

    static constraints = {
        term nullable: true
        batchId nullable: false
        max nullable: true, min: 1
        score nullable: true, range: 0..100
        offset nullable: true, min: 0
        status nullable: true, validator: { List<String> val, MappingSuggestionIndexCommand obj ->
            List<String> validStatusList = MappingSuggestionStatusUtils.possibleValues()
            val == null || val.every { validStatusList.contains(it) }
        }
    }
}