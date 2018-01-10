package org.modelcatalogue.core.gorm

import grails.gorm.DetachedCriteria
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionCountRequest
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionRequest
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionStatus
import org.modelcatalogue.core.mappingsuggestions.MappingSuggestionType
import org.springframework.context.MessageSource

@Slf4j
@CompileStatic
class MappingSuggestionsGormService implements WarnGormErrors {

    MessageSource messageSource

    @Transactional
    @ReadOnly
    List<MappingSuggestionsGormEntity> findAll(MappingSuggestionRequest req) {
        DetachedCriteria<MappingSuggestionsGormEntity> query = buildQuery(req.batchId, req.statusList, req.scorePercentage, req.term)
        query.max(req.max).offset(req.offset).list()
    }

    private DetachedCriteria<MappingSuggestionsGormEntity> buildQuery(Long batchId,
                                                                      List<MappingSuggestionStatus> statusList,
                                                                      Integer scorePercentage,
                                                                      String term) {
        DetachedCriteria<MappingSuggestionsGormEntity> query = MappingSuggestionsGormEntity.where {
            batch.id == batchId && status in statusList && result >= (scorePercentage / 100.0)
        }
        if ( term ) {
            query = query.where {
                sourceName =~ '%'+term+'%' || sourceCode =~ '%'+term+'%' || destinationName =~ '%'+term+'%' || destinationCode =~ '%'+term+'%'
            }

        }
        query
    }

    @ReadOnly
    Integer count(MappingSuggestionCountRequest req) {
        DetachedCriteria<MappingSuggestionsGormEntity> query = buildQuery(req.batchId, req.statusList, req.scorePercentage, req.term)
        query.count() as Integer
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