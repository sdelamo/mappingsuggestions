import org.modelcatalogue.core.gorm.DataElementGormService
import org.modelcatalogue.core.gorm.DataModelGormService
import org.modelcatalogue.core.gorm.DataModelMappingSuggestionsGormService
import org.modelcatalogue.core.gorm.MappingSuggestionsGormService
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionsService
import org.springframework.context.MessageSource

// Place your Spring DSL code here
beans = {
    mappingsSuggestionsGateway(MappingSuggestionsService) {
        dataModelGormService = ref('dataModelGormService')
        dataElementGormService = ref('dataElementGormService')
        dataModelMappingSuggestionsGormService = ref('dataModelMappingSuggestionsGormService')
        mappingSuggestionsGormService = ref('mappingSuggestionsGormService')
        messageSource = ref('messageSource')
    }
}
