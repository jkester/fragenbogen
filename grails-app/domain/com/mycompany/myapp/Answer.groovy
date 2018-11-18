package com.mycompany.myapp

class Answer {

    String answer
    Question question
    User user

    static constraints = {
        answer nullable: true
    }
}
