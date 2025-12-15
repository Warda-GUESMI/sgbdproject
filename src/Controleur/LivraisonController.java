package controleur;

import comgestion.ConnexionBD;
import comgestion.model.Livraison;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivraisonController {

    public List<Livraison> getLivraisons() throws SQLException {
        List<Livraison> list = new ArrayList<>();

        String sql = "SELECT * FROM LIVRAISON"; // ⚠️ inchangé

        Connection cn = ConnexionBD.getConnection();
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Livraison l = new Livraison(
                    rs.getInt("NOCDE"),
                    rs.getTimestamp("DATELIV").toLocalDateTime(),
                    rs.getInt("LIVREUR"),
                    rs.getString("MODEPAY"),
                    rs.getString("ETATLIV")
            );
            list.add(l);
        }
        return list;
    }
}
