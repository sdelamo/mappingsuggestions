package org.modelcatalogue.core.mappingsuggestions

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.modelcatalogue.core.gorm.DataElement
import org.modelcatalogue.core.gorm.DataElementGormService
import org.modelcatalogue.core.gorm.DataModel
import org.modelcatalogue.core.gorm.DataModelGormService
import org.modelcatalogue.core.gorm.DataModelMappingSuggestions
import org.modelcatalogue.core.gorm.DataModelMappingSuggestionsGormService
import org.modelcatalogue.core.gorm.MappingSuggestionsGormEntity
import org.modelcatalogue.core.gorm.MappingSuggestionsGormService
import org.modelcatalogue.core.gorm.WarnGormErrors
import org.simmetrics.StringMetric
import org.simmetrics.metrics.StringMetrics
import org.springframework.context.MessageSource

@Slf4j
@CompileStatic
class MappingSuggestionsService implements MappingsSuggestionsGateway, WarnGormErrors {

    DataModelGormService dataModelGormService

    DataElementGormService dataElementGormService

    DataModelMappingSuggestionsGormService dataModelMappingSuggestionsGormService

    MappingSuggestionsGormService mappingSuggestionsGormService

    MessageSource messageSource

    @Override
    MappingSuggestionResponse findAll(MappingSuggestionRequest req) {
        DataModelMappingSuggestions dataModelMappingSuggestionsInstance = dataModelMappingSuggestionsGormService.find(req.batchId)
        List<MappingSuggestionsGormEntity> mappingSuggestionsList = mappingSuggestionsGormService.findAll(req)

        new MappingSuggestionResponseImpl(
            id: dataModelMappingSuggestionsInstance.id,
            sourceId: dataModelMappingSuggestionsInstance.source.id,
            sourceName:  dataModelMappingSuggestionsInstance.source.name,
            destinationId: dataModelMappingSuggestionsInstance.destination.id,
            destinationName: dataModelMappingSuggestionsInstance.destination.name,
            mappingSuggestionList: mappingSuggestionsList.collect { MappingSuggestionsGormEntity gormEntity ->
                new MappingSuggestionImpl(
                    score: gormEntity.result,
                    mappingSuggestionId: gormEntity.id,
                    source: instantiateElementCompared(gormEntity.sourceId, gormEntity.sourceType),
                    destination: instantiateElementCompared(gormEntity.destinationId, gormEntity.destinationType),
                    status: gormEntity.status
                )
            } as List<MappingSuggestion>)
    }

    ElementCompared instantiateElementCompared(Long id, MappingSuggestionType type) {
        if ( type == MappingSuggestionType.DATA_ELEMENT ) {
            DataElement dataElement = dataElementGormService.find(id)
            return new ElementComparedImpl(id: dataElement.id,
                    code: dataElement.modelCatalogueId,
                    name: dataElement.name
            )
        }
        null
    }

    @Override
    Integer count(MappingSuggestionCountRequest req) {
        mappingSuggestionsGormService.count(req)
    }

    @Override
    void reject(List<Long> actionIds) {
        mappingSuggestionsGormService.changeStatus(actionIds, MappingSuggestionStatus.REJECTED)
    }

    @Override
    void approve(List<Long> actionIds) {
        mappingSuggestionsGormService.changeStatus(actionIds, MappingSuggestionStatus.APPROVED)
    }

    @Override
    void approveAll(Long batchId) {
        mappingSuggestionsGormService.changeAllStatus(batchId, MappingSuggestionStatus.APPROVED)
    }

    @Override
    void generateMappingSuggestions(GenerateMappingSuggestionsRequest request) {

        DataModelMappingSuggestions dataModelMappingSuggestions = new DataModelMappingSuggestions()
        DataModel source = dataModelGormService.find(request.sourceId)
        dataModelMappingSuggestions.source = source

        DataModel destination = dataModelGormService.find(request.destinationId)
        dataModelMappingSuggestions.destination = destination
        List<MappingSuggestionsGormEntity> mappingSuggestionsList = generateMappingSuggestionsList(request.sourceId, request.destinationId)
        mappingSuggestionsList.each { MappingSuggestionsGormEntity mappingSuggestion ->
            dataModelMappingSuggestions.addToSuggestions(mappingSuggestion)
        }
        if ( !dataModelMappingSuggestions.save() ) {
            log.warn '{}', beanWarnMessage(dataModelMappingSuggestions, messageSource)
        }
    }

    List<MappingSuggestionsGormEntity> generateMappingSuggestionsList(Long sourceId, Long destinationId) {
        List<MappingSuggestionsGormEntity> result = []
        List<DataElement> sourceDataElementList = dataElementGormService.findByDataModel(sourceId)
        List<DataElement> destinationDataElementList = dataElementGormService.findByDataModel(destinationId)
        for ( DataElement sourceDataElement : sourceDataElementList ) {
            for ( DataElement destinationDataElement : destinationDataElementList ) {
                result << generateMappingSuggestion(sourceDataElement, destinationDataElement)
            }
        }
        result
    }

    MappingSuggestionsGormEntity generateMappingSuggestion(DataElement source, DataElement destination) {
        new MappingSuggestionsGormEntity(sourceId: source.id,
        sourceType: MappingSuggestionType.DATA_ELEMENT,
        sourceCode: source.modelCatalogueId,
        sourceName: source.name,
        destinationCode: destination.modelCatalogueId,
        destinationName: destination.name,
        destinationId: destination.id,
        destinationType: MappingSuggestionType.DATA_ELEMENT,
        result: distanceBetweenDataElements(source, destination),
        status: MappingSuggestionStatus.PENDING )
    }

    float distanceBetweenDataElements(DataElement source, DataElement destination) {
        StringMetric metric = StringMetrics.levenshtein()
        float result = metric.compare(source.name, destination.name)
        result
    }
}