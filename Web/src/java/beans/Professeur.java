/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import java.util.Date;
/**
 *
 * @author maro
 */
public class Professeur {
    private int id;
    private String nom;
    private String prenom;
    private String phone;
    private String email;
    private Date dateEmb;
    private String sexe;
    private String specialite;

    public Professeur(int id, String nom, String prenom, String phone, String email, Date dateEmb, String sexe,String specialite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.email = email;
        this.dateEmb = dateEmb;
        this.sexe = sexe;
        this.specialite = specialite;
    }
    
    public Professeur(String nom, String prenom, String phone, String email, Date dateEmb, String sexe,String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.email = email;
        this.dateEmb = dateEmb;
        this.sexe = sexe;
        this.specialite = specialite;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the dateEmb
     */
    public Date getDateEmb() {
        return dateEmb;
    }

    /**
     * @param dateEmb the dateEmb to set
     */
    public void setDateEmb(Date dateEmb) {
        this.dateEmb = dateEmb;
    }

    /**
     * @return the sexe
     */
    public String getSexe() {
        return sexe;
    }

    /**
     * @param sexe the sexe to set
     */
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    
        /**
     * @return the specialite
     */
    public String getSpecialite() {
        return specialite;
    }

    /**
     * @param sexe the specialite to set
     */
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "Professeur{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", phone=" + phone + ", email=" + email + ", dateEmb=" + dateEmb + ", sexe=" + sexe + '}';
    }
}
