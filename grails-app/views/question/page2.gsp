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
                <li><a href="${createLink(uri: '/logout')}"><g:message code="default.logout.label"/></a></li>
            </ul>
        </div>

		<g:if test="${fv.quAns != null  && fv.user != null}">
            <div id="list-question1" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>

				<g:form action="page3">
					<table>
						<thead>
							<tr>
								<th><g:message code="delta.title" default="${fv.user.username}" /></th>
							</tr>
						</thead>
                        <tbody>
                            <g:each in="${fv.quAns}" var="qa">
                                <tr>
                                    <td>
                                        ${qa.question.description}
                                    </td>
                                    <g:if test="${qa.question.possibleAnswers != null && qa.question.possibleAnswers.size() > 0}">
                                        <td>
                                            <g:set var="optVals" value="${qa.question.possibleAnswers.split("\\|")}" />
                                            <g:select name="q${qa.question.questionNumber}" from="${optVals}" value="${qa.answer}" />
                                        </td>
                                    </g:if>
                                    <g:else>
                                        <td>
                                            <g:textField id="id${qa.question.questionNumber}" name="q${qa.question.questionNumber}" value="${qa.answer}" />
                                        </td>
                                    </g:else>
                                </tr>
                            </g:each>
                            <tr>
                                <td><g:submitButton name="page3" value="page3" />
                            </tr>
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