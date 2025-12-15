package controleur;

import comgestion.ConnexionBD;
import comgestion.model.Commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeController {

    public List<Commande> getCommandes() throws SQLException {
        List<Commande> list = new ArrayList<>();

        String sql = "SELECT * FROM COMMANDE"; // ⚠️ inchangé

        Connection cn = ConnexionBD.getConnection();
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Commande c = new Commande(
                    rs.getInt("NOCDE"),
                    rs.getInt("NOCLT"),
                    rs.getDate("DATECDE").toLocalDate(),
                    rs.getString("ETATCDE")
            );
            list.add(c);
        }
        return list;
    }
}
