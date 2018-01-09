package org.modelcatalogue.core.mappingsuggestions

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class MappingSuggestionApproveAllCommandConstraintsSpec extends Specification {

    @Shared
    @Subject
    MappingSuggestionApproveAllCommand cmd

    def setupSpec() {
        cmd = new MappingSuggestionApproveAllCommand()
    }

    def cleanupSpec() {
        cmd = null
    }

    void 'status cannot be null'() {
        when:
        cmd.batchId = null

        then:
        !cmd.validate(['batchId'])
    }
}
