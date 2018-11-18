package com.mycompany.myapp

import grails.gorm.transactions.Transactional

@Transactional
class QuestionAnswerService {

    List<Question> findAllQuestions() {
        def questions = Question.findAll()
        log.info("retrieved ${questions.size()} questions")
        return questions
    }
    def serviceMethod() {

    }

    List<Answer> findAllAnswersForUser(User searchUser) {
        List<Answer> answers = Answer.findAll {
            user == searchUser
        }
        log.info("retrieved ${answers.size()} answers")
        return answers
    }

    Answer findAnswerForNumber(int qNumber, User cUser) {
        Answer a = Answer.find{question.questionNumber == qNumber && user == cUser}
        return a
    }

    //returns new and existing answers for question number start to end
    List<Answer> populateAnswers(List<Question> questions, List<Answer> existingAnswers, User cUser,int start, int end) {
        List<Answer> existingAnswersFromStartTillEnd = existingAnswers.findAll{it.question.questionNumber >= start && it.question.questionNumber <= end}
        List<Integer> existingQuestionNumbers = existingAnswersFromStartTillEnd.collect {it.question.questionNumber}
        List<Question> questionsFromStartTillEnd = questions.findAll{it.questionNumber >= start && it.questionNumber <= end}
        List<Answer> emptyAnswers = questionsFromStartTillEnd.collect { new Answer(question: it, user: cUser)}
        return emptyAnswers.findAll{existingQuestionNumbers.contains(it.question.questionNumber) == false} + existingAnswersFromStartTillEnd
    }

    def storeAnswer(int qNumber, User cUser, String value) {
        Question q = Question.find{ questionNumber == qNumber }
        Answer a = new Answer(question: q, user : cUser, answer : value)
        a.save()
        log.info("Created new answer for ${qNumber} ${cUser.username}")
    }


    def storeAnswerIfDifferent(int qNumber, User cUser, String value) {
        Answer a = findAnswerForNumber(qNumber, cUser)
        if (a != null && a.answer != value) {
            a.answer = value
            a.save()
            log.info("Updated answer for ${qNumber} ${cUser.username}")
        }
        else if(a == null) {
            storeAnswer(qNumber,cUser,value)
        }
    }




}
