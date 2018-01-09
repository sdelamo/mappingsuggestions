package org.modelcatalogue.core.gorm

import groovy.transform.CompileStatic

@CompileStatic
class DataModelMappingSuggestionsController {
    DataModelMappingSuggestionsGormService dataModelMappingSuggestionsGormService

    def index() {
        [batches: dataModelMappingSuggestionsGormService.findAll()]
    }
}