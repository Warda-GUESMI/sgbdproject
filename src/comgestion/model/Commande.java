package comgestion.model;

import java.sql.Date;

public class Commande {

    private int nocde;
    private int noclt;
    private Date datecde;
    private String etatcde;

    public Commande(int nocde, int noclt, Date datecde, String etatcde) {
        this.nocde = nocde;
        this.noclt = noclt;
        this.datecde = datecde;
        this.etatcde = etatcde;
    }

    public int getNocde() { return nocde; }
    public int getNoclt() { return noclt; }
    public Date getDatecde() { return datecde; }
    public String getEtatcde() { return etatcde; }
}
