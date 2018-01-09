package org.modelcatalogue.core.gorm

import grails.gorm.services.Service

interface DataModelMappingSuggestionsDataService {

    List<DataModelMappingSuggestions> findAll()

    DataModelMappingSuggestions find(Serializable id)
}

@Service(DataModelMappingSuggestions)
abstract class DataModelMappingSuggestionsGormService implements DataModelMappingSuggestionsDataService {

}