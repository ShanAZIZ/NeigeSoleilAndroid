package com.example.neigesoleil.models;

public class Contrat {

    private int id;
    private int user;
    private String nom;
    private String adresse;
    private String code_postale;
    private String ville;
    private String description;
    private String type;
    private String exposition;
    private float surface_habitable;
    private float surface_balcon;
    private int capacite;
    private float distance_pistes;
    private String status;
    private float prix_saison_haute;
    private float prix_saison_moyenne;
    private float prix_saison_basse;
    private String date_debut;
    private String date_fin;
    private String date_creation;

    public Contrat(int id, int user, String nom, String adresse, String code_postale, String ville, String description, String type, String exposition, float surface_habitable, float surface_balcon, int capacite, float distance_pistes, String status, float prix_saison_haute, float prix_saison_moyenne, float prix_saison_basse, String date_debut, String date_fin, String date_creation) {
        this.id = id;
        this.user = user;
        this.nom = nom;
        this.adresse = adresse;
        this.code_postale = code_postale;
        this.ville = ville;
        this.description = description;
        this.type = type;
        this.exposition = exposition;
        this.surface_habitable = surface_habitable;
        this.surface_balcon = surface_balcon;
        this.capacite = capacite;
        this.distance_pistes = distance_pistes;
        this.status = status;
        this.prix_saison_haute = prix_saison_haute;
        this.prix_saison_moyenne = prix_saison_moyenne;
        this.prix_saison_basse = prix_saison_basse;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.date_creation = date_creation;
    }

    public Contrat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExposition() {
        return exposition;
    }

    public void setExposition(String exposition) {
        this.exposition = exposition;
    }

    public float getSurface_habitable() {
        return surface_habitable;
    }

    public void setSurface_habitable(float surface_habitable) {
        this.surface_habitable = surface_habitable;
    }

    public float getSurface_balcon() {
        return surface_balcon;
    }

    public void setSurface_balcon(float surface_balcon) {
        this.surface_balcon = surface_balcon;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public float getDistance_pistes() {
        return distance_pistes;
    }

    public void setDistance_pistes(float distance_pistes) {
        this.distance_pistes = distance_pistes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getPrix_saison_haute() {
        return prix_saison_haute;
    }

    public void setPrix_saison_haute(float prix_saison_haute) {
        this.prix_saison_haute = prix_saison_haute;
    }

    public float getPrix_saison_moyenne() {
        return prix_saison_moyenne;
    }

    public void setPrix_saison_moyenne(float prix_saison_moyenne) {
        this.prix_saison_moyenne = prix_saison_moyenne;
    }

    public float getPrix_saison_basse() {
        return prix_saison_basse;
    }

    public void setPrix_saison_basse(float prix_saison_basse) {
        this.prix_saison_basse = prix_saison_basse;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public String detailString() {
        String detail =
                "Description : \n" + this.description + "\n\n" +
                        "Adresse : " + this.adresse + ", " + this.ville + " " + this.code_postale + "\n" +
                        "Type : " + this.type + "  " +
                        "Capacite : " + this.capacite + "Personnes \n" +
                        "Surface Totale : " + this.surface_habitable + this.surface_balcon + " m2 " +
                        "Exposition : " + this.exposition + "\n" +
                        "Prix Hebdomadaire : " + this.prix_saison_moyenne + "€";

        return  detail;
    }

    @Override
    public String toString() {
        return "Nom : " + this.nom + "\n" +
                "Type : " + this.type + "\n" +
                "Surface Totale : " + this.surface_habitable + this.surface_balcon + " m2\n" +
                "Exposition : " + this.exposition + "\n" +
                "Prix Hebdomadaire : " + this.prix_saison_moyenne + "€";
    }
}
