package com.example.neigesoleil.models;

public class Profile {

    private int user;
    private int id;
    private String adresse;
    private String code_postale;
    private String ville;
    private String telephone;
    private String rib;

    public Profile(int user, int id, String adresse, String code_postale, String ville, String telephone, String rib) {
        this.user = user;
        this.id = id;
        this.adresse = adresse;
        this.code_postale = code_postale;
        this.ville = ville;
        this.telephone = telephone;
        this.rib = rib;
    }

    public Profile(int user, String adresse, String code_postale, String ville, String telephone, String rib) {
        this.user = user;
        this.adresse = adresse;
        this.code_postale = code_postale;
        this.ville = ville;
        this.telephone = telephone;
        this.rib = rib;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCode_postale() {
        return code_postale;
    }

    public void setCode_postale(String code_postale) {
        this.code_postale = code_postale;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }
}


