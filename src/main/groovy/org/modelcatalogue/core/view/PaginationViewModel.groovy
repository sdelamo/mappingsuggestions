package org.modelcatalogue.core.view

import groovy.transform.CompileStatic

@CompileStatic
interface PaginationViewModel {
    Integer getTotal()
    Integer getMax()
    Integer getOffset()
}