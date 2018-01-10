package org.modelcatalogue.core.mappingsuggestions

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic

@CompileStatic
class MappingSuggestionsController implements GrailsConfigurationAware {

    static allowedMethods = [
            index: 'GET',
            reject: 'POST',
            approve: 'POST',
            approveAll: 'POST',
    ]

    Integer defaultMax
    Integer defaultScore

    MappingsSuggestionsGateway mappingsSuggestionsGateway

    @Override
    void setConfiguration(Config co) {
        defaultMax = co.getProperty('mdx.mappingsuggestions.max', Integer, 20)
        defaultScore = co.getProperty('mdx.mappingsuggestions.score', Integer, 20)
    }

    def index(MappingSuggestionIndexCommand cmd) {
        if ( cmd.hasErrors() ) {
            render status: 422
            return
        }
        MappingSuggestionRequest mappingSuggestionRequest = new MappingSuggestionRequestImpl(
                batchId: cmd.batchId,
                max: cmd.max ?: defaultMax,
                offset: cmd.offset ?: 0,
                scorePercentage: cmd.score ?: defaultScore,
                statusList: (cmd.status ?: MappingSuggestionStatusUtils.possibleValues()).collect {
                    MappingSuggestionStatusUtils.of(it)
                },
                term: cmd.term
        )
        MappingSuggestionResponse rsp = mappingsSuggestionsGateway.findAll(mappingSuggestionRequest)
        Integer total = mappingsSuggestionsGateway.count(mappingSuggestionRequest)
        [
                score: mappingSuggestionRequest.scorePercentage,
                statusList: mappingSuggestionRequest.statusList.collect { it.name() },
                total: total,
                max: mappingSuggestionRequest.max,
                offset: mappingSuggestionRequest.offset,
                mappingSuggestionResponse: rsp,
                term: mappingSuggestionRequest.term,
        ]
    }

    def reject(MappingSuggestionRejectCommand cmd) {
        if ( cmd.hasErrors() ) {
            redirect action: 'index', params: [batchId: cmd.batchId]
            return
        }

        mappingsSuggestionsGateway.reject(cmd.mappingSuggestionIds)
        redirect action: 'index', params: [batchId: cmd.batchId]
    }

    def approve(MappingSuggestionApproveCommand cmd) {
        if ( cmd.hasErrors() ) {
            redirect action: 'index', params: [batchId: cmd.batchId]
            return
        }

        mappingsSuggestionsGateway.approve(cmd.mappingSuggestionIds)
        redirect action: 'index', params: [batchId: cmd.batchId]
    }

    def approveAll(MappingSuggestionApproveAllCommand cmd) {
        mappingsSuggestionsGateway.approveAll(cmd.batchId)
        redirect action: 'index', params: [batchId: cmd.batchId]
    }

}