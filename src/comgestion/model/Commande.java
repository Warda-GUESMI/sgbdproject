package comgestion.model;

import java.time.LocalDate;

public class Commande {

    private int nocde;
    private int noclt;
    private LocalDate datecde;
    private String etatcde;

    public Commande(int nocde, int noclt, LocalDate datecde, String etatcde) {
        this.nocde = nocde;
        this.noclt = noclt;
        this.datecde = datecde;
        this.etatcde = etatcde;
    }

    // GETTERS
    public int getNocde() { return nocde; }
    public int getNoclt() { return noclt; }
    public LocalDate getDatecde() { return datecde; }
    public String getEtatcde() { return etatcde; }
    
    // SETTERS
    public void setNocde(int nocde) { this.nocde = nocde; }
    public void setNoclt(int noclt) { this.noclt = noclt; }
    public void setDatecde(LocalDate datecde) { this.datecde = datecde; }
    public void setEtatcde(String etatcde) { this.etatcde = etatcde; }
}