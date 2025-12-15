package comgestion.model;

import java.sql.Date;

public class Livraison {

    private int nocde;
    private Date dateliv;
    private int livreur;
    private String modepay;
    private String etatliv;

    public Livraison(int nocde, Date dateliv, int livreur,
                     String modepay, String etatliv) {
        this.nocde = nocde;
        this.dateliv = dateliv;
        this.livreur = livreur;
        this.modepay = modepay;
        this.etatliv = etatliv;
    }

    public int getNocde() { return nocde; }
    public Date getDateliv() { return dateliv; }
    public int getLivreur() { return livreur; }
    public String getModepay() { return modepay; }
    public String getEtatliv() { return etatliv; }
    
}
