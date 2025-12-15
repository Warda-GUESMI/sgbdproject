package comgestion.model;

public class Article {

    private String refart;
    private String designation;
    private double prixA;
    private double prixV;
    private int codetva;
    private String categorie;
    private int qtestk;
    private int supp;

    public Article(String refart, String designation, double prixA, double prixV, String categorie, int codetva, int qtestk) {
        this.refart = refart;
        this.designation = designation;
        this.prixA = prixA;
        this.prixV = prixV;
        this.codetva = codetva;
        this.categorie = categorie;
        this.qtestk = qtestk;
        this.supp = supp;
    }

    // GETTERS
    public String getRefart() { return refart; }
    public String getDesignation() { return designation; }
    public double getPrixA() { return prixA; }
    public double getPrixV() { return prixV; }
    public int getCodetva() { return codetva; }
    public String getCategorie() { return categorie; }
    public int getQtestk() { return qtestk; }
    public int getSupp() { return supp; }
}
