package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic

@CompileStatic
interface ElementCompared {
    Long getId()
    String getCode()
    String getName()
}