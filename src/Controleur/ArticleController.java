package controleur;

import comgestion.ConnexionBD;
import comgestion.model.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleController {

    public List<Article> getArticles() throws SQLException {
        List<Article> list = new ArrayList<>();

        String sql = "SELECT * FROM ARTICLE"; // ⚠️ inchangé

        Connection cn = ConnexionBD.getConnection();
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Article a = new Article(
                    rs.getString("REFART"),
                    rs.getString("DESIGNATION"),
                    rs.getDouble("PRIXA"),
                    rs.getDouble("PRIXV"),
                    rs.getString("CATEGORIE"),
                    rs.getInt("QTESTK"),
                    rs.getInt("CODETVA")
            );
            list.add(a);
        }
        return list;
    }
}
