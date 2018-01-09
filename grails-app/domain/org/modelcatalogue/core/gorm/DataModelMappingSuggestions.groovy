package org.modelcatalogue.core.gorm

class DataModelMappingSuggestions {
    DataModel source
    DataModel destination
    Date dateCreated
    static hasMany = [suggestions: MappingSuggestionsGormEntity]
}