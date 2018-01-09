package org.modelcatalogue.core.gorm

class DataElement {
    String modelCatalogueId
    String name

    static belongsTo = [dataModel: DataModel]

}