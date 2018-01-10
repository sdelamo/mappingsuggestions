<html>
<head>
    <title><g:message code="mappingsuggestions.batches" default="Batches"/></title>
    <meta name="layout" content="main" />
</head>
<body>
<div class="container">
    <h1><i class="fas fa-bolt"></i> <g:message code="batches.title" default="Batches"/></h1>

    <table>
        <tbody>
        <g:each var="batch" in="${batches}">
            <tr>
                <td><g:link controller="mappingSuggestions"
                            action="index"
                            params="[batchId: batch.id]"># ${batch.id} ${batch.dateCreated}</g:link></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>

</body>
</html>