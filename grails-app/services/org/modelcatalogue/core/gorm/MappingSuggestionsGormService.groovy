package org.modelcatalogue.core.gorm

import grails.gorm.DetachedCriteria
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionCountRequest
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionRequest
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionStatus
import org.springframework.context.MessageSource

@Slf4j
@CompileStatic
class MappingSuggestionsGormService implements WarnGormErrors {

    MessageSource messageSource

    @Transactional
    @ReadOnly
    List<MappingSuggestionsGormEntity> findAll(MappingSuggestionRequest req) {
        MappingSuggestionsGormEntity.where {
            batch.id == req.batchId && status in req.statusList
        }.max(req.max).offset(req.offset).list()
    }

    @ReadOnly
    Integer count(MappingSuggestionCountRequest req) {
        MappingSuggestionsGormEntity.where {
            batch.id == req.batchId && status in req.statusList
        }.count() as Integer
    }

    @Transactional
    Number changeStatus(List<Long> ids, MappingSuggestionStatus statusParam) {
        DetachedCriteria<MappingSuggestionsGormEntity> query = MappingSuggestionsGormEntity.where {
            id in ids
        }
        query.updateAll(status:statusParam)
    }

    @Transactional
    void changeAllStatus(Long batchIdParam, MappingSuggestionStatus statusParam) {
        DetachedCriteria<MappingSuggestionsGormEntity> query = MappingSuggestionsGormEntity.where {
            batch.id == batchIdParam
        }
        for ( MappingSuggestionsGormEntity gormEntity : query.list()) {
            gormEntity.status = statusParam
            if ( !gormEntity.save() ) {
                log.warn(beanWarnMessage(gormEntity, messageSource))
            }
        }
    }
}