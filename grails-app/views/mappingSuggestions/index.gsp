<html>
<head>
    <title><g:message code="mappingsuggestions.title" default="Mapping Suggestions"/></title>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
<g:form controller="mappingSuggestions" method="POST">
    <g:hiddenField name="batchId" value="${mappingSuggestionResponse.id}"/>
    <table>
        <thead>
        <tr>
        <tr>
            <th rowspan="2"></th>
            <th colspan="2" class="align-center">${mappingSuggestionResponse.sourceName}</th>
            <th colspan="2" class="align-center">${mappingSuggestionResponse.destinationName}</th>
            <th rowspan="2"><g:message code="mappingsuggestions.score" default="Score"/></th>
            <th rowspan="2"><g:message code="mappingsuggestions.association" default="Association"/></th>
        </tr>
        <tr>
            <th><g:message code="mappingsuggestions.id" default="ID"/></th>
            <th><g:message code="mappingsuggestions.name" default="Name"/></th>
            <th><g:message code="mappingsuggestions.id" default="ID"/></th>
            <th><g:message code="mappingsuggestions.name" default="Name"/></th>
        </tr>
        </thead>
        <tbody>
        <g:each var="mappingSuggestionInstance" in="${mappingSuggestionResponse.mappingSuggestionList}">
            <tr>
                <td><g:checkBox name="mappingSuggestionIds" value="${mappingSuggestionInstance.mappingSuggestionId}" checked="false"  /></td>
                <td>${mappingSuggestionInstance.source.code}</td>
                <td>${mappingSuggestionInstance.source.name}</td>
                <td>${mappingSuggestionInstance.destination.code}</td>
                <td>${mappingSuggestionInstance.destination.name}</td>
                <td>${mappingSuggestionInstance.score}</td>
                <td>${mappingSuggestionInstance.status}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <g:actionSubmit value="${g.message(code:'mappingsuggestions.approve', default: 'Approve')}" action="approve" />
    <g:actionSubmit value="${g.message(code:'mappingsuggestions.reject', default: 'Reject')}" action="reject" />
</g:form>

    <div class="pagination">
        <g:paginate controller="mappingSuggestions"
                    action="index"
                    total="${total}"
                    offset="${offset}"
                    max="${max}"
                    params="[batchId: mappingSuggestionResponse.id]"
        />
        <g:message code="pagination.legend" args="[offset, offset + max, total]" default="Displaying ${offset}-${offset + max} of ${total}"/>
    </div>

<g:form controller="mappingSuggestions" action='approveAll' method="POST">
    <g:hiddenField name="batchId" value="${mappingSuggestionResponse.id}"/>
    <input type="submit" value="${g.message(code:'mappingsuggestions.approveAll', default: 'Approve All')}" />
</g:form>
</div>
</body>
</html>