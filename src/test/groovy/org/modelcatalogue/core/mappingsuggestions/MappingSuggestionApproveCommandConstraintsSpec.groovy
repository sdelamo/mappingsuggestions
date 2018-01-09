package org.modelcatalogue.core.mappingsuggestions

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class MappingSuggestionApproveCommandConstraintsSpec extends Specification {

    @Shared
    @Subject
    MappingSuggestionApproveCommand cmd

    def setupSpec() {
        cmd = new MappingSuggestionApproveCommand()
    }

    def cleanupSpec() {
        cmd = null
    }

    void 'actionIds cannot be null'() {
        when:
        cmd.actionIds = null

        then:
        !cmd.validate(['actionIds'])
        cmd.errors['actionIds'].code == 'nullable'
    }

    void 'actionIds cannot be an empty list'() {
        when:
        cmd.actionIds = [1]

        then:
        cmd.validate(['actionIds'])

        when:
        cmd.actionIds = []

        then:
        !cmd.validate(['actionIds'])
        cmd.errors['actionIds'].code == 'minSize.notmet'

    }

}
