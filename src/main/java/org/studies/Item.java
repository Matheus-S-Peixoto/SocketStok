package org.studies;

public class Item {

    private int id;
    private String nome;
    private int familia_id;
    private float peso;
    private String tipo;
    private String animal;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFamilia_id() {
        return familia_id;
    }

    public void setFamilia_id(int familia_id) {
        this.familia_id = familia_id;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
}
