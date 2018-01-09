package org.modelcatalogue.core.gorm

import grails.gorm.services.Service
import groovy.transform.CompileStatic

@CompileStatic
interface DataModelDataService {
    DataModel save(String name)
    DataModel find(Serializable id)
}


@CompileStatic
@Service(DataModel)
abstract class DataModelGormService implements DataModelDataService {
}