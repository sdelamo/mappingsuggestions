<html>
<head>
    <title><g:message code="mappingsuggestions.batches" default="Batches"/></title>
    <meta name="layout" content="main" />
</head>
<body>

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

</body>
</html>