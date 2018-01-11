package org.modelcatalogue.core.view

import groovy.transform.CompileStatic

@CompileStatic
class PaginationViewModelImpl implements PaginationViewModel {
    Integer total
    Integer offset
    Integer max
}
