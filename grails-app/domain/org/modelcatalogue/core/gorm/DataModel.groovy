package org.modelcatalogue.core.gorm

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class DataModel {
    String name

    static hasMany = [dataElements: DataElement]

    static constraints = {
        name nullable: false
        dataElements nullable: true
    }
}