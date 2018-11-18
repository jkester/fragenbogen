package bookstore

import com.mycompany.myapp.Question
import com.mycompany.myapp.Role
import com.mycompany.myapp.User
import com.mycompany.myapp.UserRole

class BootStrap {

    def init = { servletContext ->
        def adminRole = new Role(authority: 'ROLE_ADMIN').save()
        def userRole = new Role(authority: 'ROLE_USER').save()

        def testUser = new User(username: 'me', password: 'me').save()
        def user1 = new User(username: 'user1', password: 'user1').save()
        def user2 = new User(username: 'user2', password: 'user2').save()
        def admin = new User(username: 'admin', enabled: true, password: 'admin').save()

        def question1 = new Question(questionNumber : 100, questionLabel: "besuch_schule",
                description: "Wie oft besuchen Sie eine andere Schule?",
                possibleAnswers: "nie|selten|manchmal|oft").save()
        def question2 = new Question(questionNumber : 101, questionLabel: "besuch_kg",
                description: "Wie oft besuchen Sie einen anderen KG?",
                possibleAnswers: "nie|selten|manchmal|oft").save()
        def question3 = new Question(questionNumber : 102, questionLabel: "anzahl_tage_besuch",
                description: "Wieviel Tage im Jahr sind Sie auf einem anderen Ort?").save()
        def question4 = new Question(questionNumber : 200, questionLabel: "essen_wie_oft",
                description: "Wie oft essen Sie am Tag?").save()
        def question5 = new Question(questionNumber : 201, questionLabel: "essen_hunger",
                description: "Haben Sie dann Hunger?",
                possibleAnswers: "nie|selten|manchmal|oft").save()

        UserRole.create admin, adminRole
        UserRole.create testUser, userRole
        UserRole.create user1, userRole
        UserRole.create user2, userRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 4
        assert Role.count() == 2
        assert UserRole.count() == 4

    }
    def destroy = {
    }
}
