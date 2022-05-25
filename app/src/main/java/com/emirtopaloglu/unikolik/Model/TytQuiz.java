package com.emirtopaloglu.unikolik.Model;

public class TytQuiz {

    String tytTurkceDogru, tytTurkceYanlis, tytSosyalDogru, tytSosyalYanlis,
    tytMatematikDogru, tytMatematikYanlis, tytFenDogru, tytFenYanlis,
            tytTurkceNet, tytSosyalNet, tytMatematikNet, tytFenNet, tytToplamNet, quizDate;

    public TytQuiz(String tytTurkceDogru, String tytTurkceYanlis, String tytSosyalDogru, String tytSosyalYanlis, String tytMatematikDogru, String tytMatematikYanlis, String tytFenDogru, String tytFenYanlis, String tytTurkceNet, String tytSosyalNet, String tytMatematikNet, String tytFenNet, String tytToplamNet, String quizDate) {
        this.tytTurkceDogru = tytTurkceDogru;
        this.tytTurkceYanlis = tytTurkceYanlis;
        this.tytSosyalDogru = tytSosyalDogru;
        this.tytSosyalYanlis = tytSosyalYanlis;
        this.tytMatematikDogru = tytMatematikDogru;
        this.tytMatematikYanlis = tytMatematikYanlis;
        this.tytFenDogru = tytFenDogru;
        this.tytFenYanlis = tytFenYanlis;
        this.tytTurkceNet = tytTurkceNet;
        this.tytSosyalNet = tytSosyalNet;
        this.tytMatematikNet = tytMatematikNet;
        this.tytFenNet = tytFenNet;
        this.tytToplamNet = tytToplamNet;
        this.quizDate = quizDate;
    }

    public String getTytTurkceDogru() {
        return tytTurkceDogru;
    }

    public String getTytTurkceYanlis() {
        return tytTurkceYanlis;
    }

    public String getTytSosyalDogru() {
        return tytSosyalDogru;
    }

    public String getTytSosyalYanlis() {
        return tytSosyalYanlis;
    }

    public String getTytMatematikDogru() {
        return tytMatematikDogru;
    }

    public String getTytMatematikYanlis() {
        return tytMatematikYanlis;
    }

    public String getTytFenDogru() {
        return tytFenDogru;
    }

    public String getTytFenYanlis() {
        return tytFenYanlis;
    }

    public String getTytTurkceNet() {
        return tytTurkceNet;
    }

    public String getTytSosyalNet() {
        return tytSosyalNet;
    }

    public String getTytMatematikNet() {
        return tytMatematikNet;
    }

    public String getTytFenNet() {
        return tytFenNet;
    }

    public String getTytToplamNet() {
        return tytToplamNet;
    }

    public String getQuizDate() {
        return quizDate;
    }
}
