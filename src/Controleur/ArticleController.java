package Controleur;

import comgestion.ConnexionBD;
import comgestion.model.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleController {

    public List<Article> getArticles() throws SQLException {
        List<Article> list = new ArrayList<>();
        
        // ⚠️ Table en minuscule selon votre schéma
        String sql = "SELECT * FROM articles WHERE supp = 0 ORDER BY refart";
        
        System.out.println("📊 Exécution de : " + sql);

        try (Connection cn = ConnexionBD.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Article a = new Article(
                        rs.getString("refart"),      // minuscule
                        rs.getString("designation"), // minuscule
                        rs.getDouble("prixA"),       // minuscule
                        rs.getDouble("prixV"),       // minuscule
                        rs.getString("categorie"),   // minuscule
                        rs.getInt("codetva"),        // minuscule
                        rs.getInt("qtestk")          // minuscule
                );
                list.add(a);
            }
            System.out.println("✅ " + list.size() + " articles chargés");
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return list;
    }
    
    /**
     * Ajoute un article
     */
    public void ajouterArticle(Article article) throws SQLException {
        String sql = "INSERT INTO articles(refart, designation, prixA, prixV, codetva, categorie, qtestk, supp) " +
                     "VALUES(?, ?, ?, ?, ?, ?, ?, 0)";
        
        try (Connection cn = ConnexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, article.getRefart());
            ps.setString(2, article.getDesignation());
            ps.setDouble(3, article.getPrixA());
            ps.setDouble(4, article.getPrixV());
            ps.setInt(5, article.getCodetva());
            ps.setString(6, article.getCategorie());
            ps.setInt(7, article.getQtestk());
            
            ps.executeUpdate();
            System.out.println("✅ Article ajouté : " + article.getRefart());
        }
    }
    
    /**
     * Modifie un article
     */
    public void modifierArticle(Article article) throws SQLException {
        String sql = "UPDATE articles SET designation=?, prixA=?, prixV=?, codetva=?, categorie=?, qtestk=? " +
                     "WHERE refart=? AND supp=0";
        
        try (Connection cn = ConnexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, article.getDesignation());
            ps.setDouble(2, article.getPrixA());
            ps.setDouble(3, article.getPrixV());
            ps.setInt(4, article.getCodetva());
            ps.setString(5, article.getCategorie());
            ps.setInt(6, article.getQtestk());
            ps.setString(7, article.getRefart());
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Article modifié : " + article.getRefart());
            } else {
                throw new SQLException("Article non trouvé ou supprimé");
            }
        }
    }
    
    /**
     * Supprime un article (logique)
     */
    public void supprimerArticle(String refart) throws SQLException {
        String sql = "UPDATE articles SET supp=1 WHERE refart=?";
        
        try (Connection cn = ConnexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, refart);
            ps.executeUpdate();
            System.out.println("✅ Article supprimé (logique) : " + refart);
        }
    }
}