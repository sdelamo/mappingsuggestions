package org.modelcatalogue.core.mappingsuggestions

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic
import org.modelcatalogue.core.mappingsuggestions.view.MappingSuggestionsFilter
import org.modelcatalogue.core.mappingsuggestions.view.MappingSuggestionsFilterImpl
import org.modelcatalogue.core.view.PaginationViewModel
import org.modelcatalogue.core.view.PaginationViewModelImpl

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
        MappingSuggestionsFilter mappingSuggestionsFilter = new MappingSuggestionsFilterImpl(score: mappingSuggestionRequest.scorePercentage,
                statusList: mappingSuggestionRequest.statusList.collect { it.name() },
                term: mappingSuggestionRequest.term,
                max: mappingSuggestionRequest.max)
        PaginationViewModel pagination = new PaginationViewModelImpl(total: total,
                max: mappingSuggestionRequest.max,
                offset: mappingSuggestionRequest.offset)
        [
                pagination: pagination,
                filter: mappingSuggestionsFilter,
                mappingSuggestionList: rsp.mappingSuggestionList,
                sourceName: rsp.sourceName,
                destinationName: rsp.destinationName,
                batchId: cmd.batchId
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
        if ( cmd.hasErrors() ) {
            redirect uri: '/'
            return
        }
        mappingsSuggestionsGateway.approveAll(cmd.batchId)
        redirect action: 'index', params: [batchId: cmd.batchId]
    }

}