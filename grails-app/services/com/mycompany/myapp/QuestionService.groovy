package com.mycompany.myapp

import grails.gorm.services.Service


@Service(Question)
interface QuestionService {


    Question get(Serializable id)

    List<Question> list(Map args)

    Long count()

    void delete(Serializable id)

    Question save(Question question)


}