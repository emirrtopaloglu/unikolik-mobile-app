package com.emirtopaloglu.unikolik.Model;

public class DailyGoal {

    String date;
    String lesson;
    String question;
    String user;

    public DailyGoal(String date, String lesson, String question, String user) {
        this.date = date;
        this.lesson = lesson;
        this.question = question;
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public String getLesson() {
        return lesson;
    }

    public String getQuestion() {
        return question;
    }

    public String getUser() {
        return user;
    }
}
