package com.example.adelalsay.mycars.model;

public class Car {
    private int id ;
    private  String model ;
    private  String color ;
    private String descraption ;
    private String image ;
    private  Double dpl ;

    public Car(int id, String model, String color, String descraption, String image, Double dpl) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.descraption = descraption;
        this.image = image;
        this.dpl = dpl;
    }

    public Car(String model, String color, String descraption, String image, Double dpl) {
        this.model = model;
        this.color = color;
        this.descraption = descraption;
        this.image = image;
        this.dpl = dpl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescraption() {
        return descraption;
    }

    public void setDescraption(String descraption) {
        this.descraption = descraption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getDpl() {
        return dpl;
    }

    public void setDpl(Double dpl) {
        this.dpl = dpl;
    }
}
