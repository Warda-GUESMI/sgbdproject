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

    public Article(String refart, String designation, double prixA, double prixV, 
                   String categorie, int codetva, int qtestk) {
        this.refart = refart;
        this.designation = designation;
        this.prixA = prixA;
        this.prixV = prixV;
        this.categorie = categorie;
        this.codetva = codetva;
        this.qtestk = qtestk;
        this.supp = 0; // valeur par défaut
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
    
    // SETTERS
    public void setRefart(String refart) { this.refart = refart; }
    public void setDesignation(String designation) { this.designation = designation; }
    public void setPrixA(double prixA) { this.prixA = prixA; }
    public void setPrixV(double prixV) { this.prixV = prixV; }
    public void setCodetva(int codetva) { this.codetva = codetva; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setQtestk(int qtestk) { this.qtestk = qtestk; }
    public void setSupp(int supp) { this.supp = supp; }
}