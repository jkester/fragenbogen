package com.mycompany.myapp

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class QuestionController {

    QuestionService questionService
    QuestionAnswerService questionAnswerService
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond questionService.list(params), model:[questionCount: questionService.count()]
    }

    def show(Long id) {
        respond questionService.get(id)
    }

    def prepareFormVals(User cUser, int startIndex, int endIndex) {
        def formVals = [:]

        //load all questions into session memory
        List<Question> allQuestions = session["allquestions"]
        if (allQuestions == null) {
            log.info("Loading all questions")
            allQuestions = questionAnswerService.findAllQuestions()
            session["allquestions"] = allQuestions
        }
        else {
            log.info("number of questions: ${allQuestions.size()}")
        }

        //get current user
        log.info("user: ${cUser}")
        formVals.user = cUser

        //get all answers of this user
        def existingAnswers = questionAnswerService.findAllAnswersForUser(cUser)
        List<Answer> newAndExistingAnswers = questionAnswerService.populateAnswers(allQuestions,existingAnswers,cUser,startIndex,endIndex)
        newAndExistingAnswers.each {log.info(it.question.description)}
        formVals.quAns = newAndExistingAnswers
        return formVals
    }

    def processParams(def params, User cUser) {
        def qparams = params.findAll {k,v -> k.toString().startsWith("q")}
        qparams.each {k,v ->
            log.info("${k} ${v}")
            int questionNumber = k.toString().substring(1).toInteger()
            questionAnswerService.storeAnswerIfDifferent(questionNumber,cUser,v.toString())
        }
    }

    def page1() {
        log.info("START calling page1")
        def user = springSecurityService.currentUser
        //populate form with all answers for page1 (100 numbers)
        def fVals = prepareFormVals(user,100,199)
        return [fv : fVals];
    }

    def page2() {
        //store values into db
        def user = springSecurityService.currentUser
        processParams(params,user)

        //prepare page 2
        def fVals = prepareFormVals(user,200,299)
        return [fv : fVals];
    }

    def page3() {
        //store values into db
        def user = springSecurityService.currentUser
        processParams(params,user)

        //prepare page 2
        def fVals = prepareFormVals(user,300,399)
        return [fv : fVals];
    }

    def finishLastPage() {

    }

    def create() {
        respond new Question(params)
    }

    def save(Question question) {
        if (question == null) {
            notFound()
            return
        }

        try {
            questionService.save(question)
        } catch (ValidationException e) {
            respond question.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'question.label', default: 'Question'), question.id])
                redirect question
            }
            '*' { respond question, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond questionService.get(id)
    }

    def update(Question question) {
        if (question == null) {
            notFound()
            return
        }

        try {
            questionService.save(question)
        } catch (ValidationException e) {
            respond question.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'question.label', default: 'Question'), question.id])
                redirect question
            }
            '*'{ respond question, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        questionService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'question.label', default: 'Question'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
