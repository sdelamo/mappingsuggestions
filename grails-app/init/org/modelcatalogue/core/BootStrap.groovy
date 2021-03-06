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
        dataElementGormService.save('A01', 'Gluscose', cancelHospitalA)
        dataElementGormService.save('A02', 'Blood pressure', cancelHospitalA)
        dataElementGormService.save('A03', 'Body mass', cancelHospitalA)

        List<List<String>> examples = [
                ['Glicose', 'Glukos', 'Gluc', 'Glucosa', 'Glucosio', 'Glükos', 'Glikoz', '葡萄糖'],
                ['Pressione Sanguigna', 'Presión Sanguínea', 'Blodtryk', 'Blutdrukc'],
        ]
        List<String> l = examples.flatten() as List<String>
        l.eachWithIndex { String name, int index ->
            dataElementGormService.save("B${index}", name, cancelHospitalB)
        }

        GenerateMappingSuggestionsRequestImpl request = new GenerateMappingSuggestionsRequestImpl(sourceId: cancelHospitalA.id, destinationId: cancelHospitalB.id)
        mappingsSuggestionsGateway.generateMappingSuggestions(request)

    }
    def destroy = {
    }
}
