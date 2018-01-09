package org.modelcatalogue.core

import groovy.transform.CompileStatic
import org.modelcatalogue.core.gorm.DataElement
import org.modelcatalogue.core.gorm.DataElementGormService
import org.modelcatalogue.core.gorm.DataModel
import org.modelcatalogue.core.gorm.DataModelDataService
import org.modelcatalogue.core.gorm.DataModelGormService
import org.modelcatalogue.core.mappingsuggestions.GenerateMappingSuggestionsRequestImpl
import org.modelcatalogue.core.mappingsuggestions.MappingsSuggestionsGateway

@CompileStatic
class BootStrap {

    DataModelGormService dataModelGormService
    DataElementGormService dataElementGormService
    MappingsSuggestionsGateway mappingsSuggestionsGateway
    def init = { servletContext ->

        DataModel cancelHospitalA = dataModelGormService.save('Cancer Hospital A')
        DataModel cancelHospitalB = dataModelGormService.save('Cancer Hospital B')
        DataElement cancelHospitalAGlucose = dataElementGormService.save('A01', 'Gluscose', cancelHospitalA)
        DataElement cancelHospitalBGlucose = dataElementGormService.save('B01', 'Glicose', cancelHospitalB)

        GenerateMappingSuggestionsRequestImpl request = new GenerateMappingSuggestionsRequestImpl(sourceId: cancelHospitalA.id, destinationId: cancelHospitalBGlucose.id)
        mappingsSuggestionsGateway.generateMappingSuggestions(request)

    }
    def destroy = {
    }
}
