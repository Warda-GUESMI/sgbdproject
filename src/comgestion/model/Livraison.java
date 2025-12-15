package comgestion.model;

import java.time.LocalDateTime;

public class Livraison {

    private int nocde;
    private LocalDateTime dateliv;
    private int livreur;
    private String modepay;
    private String etatliv;

    public Livraison(int nocde, LocalDateTime dateliv, int livreur,
                     String modepay, String etatliv) {
        this.nocde = nocde;
        this.dateliv = dateliv;
        this.livreur = livreur;
        this.modepay = modepay;
        this.etatliv = etatliv;
    }

    // GETTERS
    public int getNocde() { return nocde; }
    public LocalDateTime getDateliv() { return dateliv; }
    public int getLivreur() { return livreur; }
    public String getModepay() { return modepay; }
    public String getEtatliv() { return etatliv; }
    
    // SETTERS
    public void setNocde(int nocde) { this.nocde = nocde; }
    public void setDateliv(LocalDateTime dateliv) { this.dateliv = dateliv; }
    public void setLivreur(int livreur) { this.livreur = livreur; }
    public void setModepay(String modepay) { this.modepay = modepay; }
    public void setEtatliv(String etatliv) { this.etatliv = etatliv; }
}