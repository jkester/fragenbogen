<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-question" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/question')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="page2">page2</g:link></li>
            </ul>
        </div>

		<g:if test="${fv.quAns != null}">
            <div id="list-question1" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>

				<g:form action="page2">
					<table>
						<thead>
							<tr>
								<th><g:message code="delta.title" default="${fv.user.username}" /></th>
							</tr>
						</thead>
                        <tbody>
                            <tr>
                                <td><g:textArea name="myField" class="raw"
                                        value="XXXX" rows="10" cols="100"
                                        readonly="readonly" /></td>
                            </tr>
                            <g:each in="${fv.quAns}" var="qa">
                                <tr>
                                    <td>
                                        ${qa.question.description}
                                    </td>
                                </tr>
                            </g:each>
                        </tbody>
					</table>
				</g:form>

            </div>

		</g:if>


        <div id="list-question1" class="content scaffold-list" role="main">
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${questionList}" />

            <div class="pagination">
                <g:paginate total="${questionCount ?: 0}" />
            </div>
        </div>
    </body>
</html>