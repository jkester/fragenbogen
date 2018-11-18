package com.mycompany.myapp

class Question {

    int questionNumber
    String questionLabel
    String description
    String possibleAnswers

    static constraints = {
        questionLabel blank: false, unique: true
        possibleAnswers nullable: true
    }
}
