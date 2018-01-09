package org.modelcatalogue.core.gorm

import grails.gorm.services.Service
import grails.gorm.transactions.ReadOnly
import groovy.transform.CompileStatic

@CompileStatic
interface DataElementDataService {
    DataElement save(String modelCatalogueId, String name, DataModel dataModel)
    DataElement find(Serializable id)
}

@CompileStatic
@Service(DataElement)
abstract class DataElementGormService implements DataElementDataService {
    @ReadOnly
    List<DataElement> findByDataModel(Long dataModelId) {
        DataElement.where { dataModel.id == dataModelId }.list()
    }
}