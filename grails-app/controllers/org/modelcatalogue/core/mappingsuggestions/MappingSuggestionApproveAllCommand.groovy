package org.modelcatalogue.core.mappingsuggestions

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class MappingSuggestionApproveAllCommand implements Validateable {
    Long batchId

    static constraints = {
        batchId nullable: false
    }
}