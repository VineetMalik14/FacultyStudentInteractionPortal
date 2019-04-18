package com.example.does;

public class Mytodo {
    String titledoes, keydoes;

    public Mytodo() {
    }


    public Mytodo(String titledoes, String keydoes) {
        this.titledoes = titledoes;
        this.keydoes = keydoes;
    }

    public String getKeydoes() {
        return keydoes;
    }

    public void setKeydoes(String keydoes) {
        this.keydoes = keydoes;
    }

    public String getTitledoes() {
        return titledoes;
    }

    public void setTitledoes(String titledoes) {
        this.titledoes = titledoes;
    }
}