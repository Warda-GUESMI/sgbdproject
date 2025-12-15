package Controleur;

import comgestion.ConnexionBD;
import comgestion.model.Commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeController {

    public List<Commande> getCommandes() throws SQLException {
        List<Commande> list = new ArrayList<>();
        
        // ⚠️ Table en minuscule selon votre schéma
        String sql = "SELECT c.nocde, c.noclt, c.datecde, c.etatcde " +
                     "FROM commandes c " +
                     "WHERE c.etatcde != 'AN' " +
                     "ORDER BY c.datecde DESC";
        
        System.out.println("📊 Exécution de : " + sql);

        try (Connection cn = ConnexionBD.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Commande c = new Commande(
                        rs.getInt("nocde"),
                        rs.getInt("noclt"),
                        rs.getDate("datecde").toLocalDate(),
                        rs.getString("etatcde")
                );
                list.add(c);
            }
            System.out.println("✅ " + list.size() + " commandes chargées");
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return list;
    }
    
    /**
     * Récupère les détails d'une commande avec le nom du client
     */
    public String getNomClient(int noclt) throws SQLException {
        String sql = "SELECT nomclt, prenomclt FROM clients WHERE noclt = ?";
        
        try (Connection cn = ConnexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, noclt);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String nom = rs.getString("nomclt");
                String prenom = rs.getString("prenomclt");
                return prenom != null ? nom + " " + prenom : nom;
            }
            return "Client #" + noclt;
        }
    }
    
    /**
     * Décode l'état de la commande
     */
    public static String decodeEtat(String etat) {
        switch (etat) {
            case "EC": return "En cours";
            case "PR": return "Prête";
            case "LI": return "Livrée";
            case "SO": return "Sortie";
            case "AN": return "Annulée";
            case "AL": return "Autre";
            default: return etat;
        }
    }
}