package Controleur;

import comgestion.ConnexionBD;
import comgestion.model.Livraison;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LivraisonController {

    public List<Livraison> getLivraisons() throws SQLException {
        List<Livraison> list = new ArrayList<>();
        
        // ⚠️ NOM EXACT DE VOTRE TABLE
        String sql = "SELECT nocde, dateliv, livreur, modepay, etatliv " +
                     "FROM LivraisonCom " +
                     "ORDER BY dateliv DESC";
        
        System.out.println("📊 Exécution de : " + sql);

        try (Connection cn = ConnexionBD.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                // Gestion flexible DATE ou TIMESTAMP
                LocalDateTime dateTime;
                try {
                    // Essayer d'abord comme TIMESTAMP
                    Timestamp ts = rs.getTimestamp("dateliv");
                    if (ts != null) {
                        dateTime = ts.toLocalDateTime();
                    } else {
                        // Sinon comme DATE
                        Date date = rs.getDate("dateliv");
                        dateTime = date != null ? date.toLocalDate().atStartOfDay() : LocalDateTime.now();
                    }
                } catch (Exception e) {
                    // Par défaut
                    dateTime = LocalDateTime.now();
                    System.err.println("⚠️ Erreur conversion date pour commande " + rs.getInt("nocde"));
                }
                
                Livraison l = new Livraison(
                        rs.getInt("nocde"),
                        dateTime,
                        rs.getInt("livreur"),
                        rs.getString("modepay"),
                        rs.getString("etatliv")
                );
                list.add(l);
            }
            System.out.println("✅ " + list.size() + " livraisons chargées");
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
            System.err.println("❌ Requête : " + sql);
            e.printStackTrace();
            throw e;
        }
        return list;
    }
    
    /**
     * Récupère le nom du livreur
     */
    public String getNomLivreur(int idpers) throws SQLException {
        String sql = "SELECT nompers, prenompers FROM personnel WHERE idpers = ?";
        
        try (Connection cn = ConnexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, idpers);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("nompers") + " " + rs.getString("prenompers");
            }
            return "Livreur #" + idpers;
        }
    }
    
    /**
     * Décode l'état de la livraison
     */
    public static String decodeEtat(String etat) {
        if (etat == null) return "Inconnu";
        switch (etat.trim()) {
            case "EC": return "En cours";
            case "LI": return "Livrée";
            case "AL": return "Autre";
            default: return etat;
        }
    }
    
    /**
     * Décode le mode de paiement
     */
    public static String decodeModePay(String mode) {
        if (mode == null) return "Inconnu";
        switch (mode.trim()) {
            case "avant_livraison": return "Avant livraison";
            case "apres_livraison": return "Après livraison";
            default: return mode;
        }
    }
}