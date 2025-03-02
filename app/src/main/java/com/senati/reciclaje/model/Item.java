package com.senati.reciclaje.model;

/**
 * Entidad Item. Platilla requiere parametros "UserId, numPlastico, numCarton,
 * numMetal, numElectronico"
 */
public class Item {
    private Integer itemId;
    private double userId;
    private double numPlastico;
    private double numCarton;
    private double numMetal;
    private double numElectronico;

    public Item(Integer itemId, int userId, double numPlastico, double numCarton, double numMetal, double numElectronico) {
        this.itemId = itemId;
        this.userId = userId;
        this.numPlastico = numPlastico;
        this.numCarton = numCarton;
        this.numMetal = numMetal;
        this.numElectronico = numElectronico;
    }

    public double getItemId(){
        return itemId;
    }

    public void setItemId(int itemId){
        this.itemId = itemId;
    }

    public double getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getNumPlastico() {
        return numPlastico;
    }

    public void setNumPlastico(int numPlastico) {
        this.numPlastico = numPlastico;
    }

    public double getNumCarton() {
        return numCarton;
    }

    public void setNumCarton(int numCarton) {
        this.numCarton = numCarton;
    }

    public double getNumMetal() {
        return numMetal;
    }

    public void setNumMetal(int numMetal) {
        this.numMetal = numMetal;
    }

    public double getNumElectronico() {
        return numElectronico;
    }

    public void setNumElectronico(int numElectronico) {
        this.numElectronico = numElectronico;
    }
}
