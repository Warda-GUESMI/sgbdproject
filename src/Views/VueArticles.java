package views;

import Controler.ArticleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import comgestion.model.Article;

public class VueArticles extends BorderPane {

    private TableView<Article> table;
    private ObservableList<Article> data;
    private TextField txtRef, txtDes, txtPrixA, txtPrixV, txtStock, txtTVA;
    private ComboBox<String> cmbCat;

    public VueArticles() {
        setPadding(new Insets(10));
        setTop(formulaire());
        setCenter(tableau());
        charger();
    }

    private VBox formulaire() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        txtRef = new TextField();
        txtDes = new TextField();
        txtPrixA = new TextField();
        txtPrixV = new TextField();
        txtStock = new TextField();
        txtTVA = new TextField();

        cmbCat = new ComboBox<>();
        cmbCat.getItems().addAll("Info", "Audio", "Telephone", "Tablette");

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(8);

        g.addRow(0, new Label("Réf"), txtRef,
                    new Label("Désignation"), txtDes);
        g.addRow(1, new Label("Prix Achat"), txtPrixA,
                    new Label("Prix Vente"), txtPrixV);
        g.addRow(2, new Label("Stock"), txtStock,
                    new Label("TVA"), txtTVA);
        g.addRow(3, new Label("Catégorie"), cmbCat);

        HBox btns = new HBox(10,
                new Button("Ajouter"),
                new Button("Modifier"),
                new Button("Supprimer")
        );

        box.getChildren().addAll(new Label("Gestion Articles"), g, btns);
        return box;
    }

    private TableView<Article> tableau() {
        table = new TableView<>();

        TableColumn<Article, String> c1 = new TableColumn<>("Ref");
        c1.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleStringProperty(d.getValue().getRefart()));

        TableColumn<Article, String> c2 = new TableColumn<>("Designation");
        c2.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleStringProperty(d.getValue().getDesignation()));

        TableColumn<Article, Number> c3 = new TableColumn<>("PrixA");
        c3.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleDoubleProperty(d.getValue().getPrixA()));

        TableColumn<Article, Number> c4 = new TableColumn<>("PrixV");
        c4.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleDoubleProperty(d.getValue().getPrixV()));

        TableColumn<Article, String> c5 = new TableColumn<>("Cat");
        c5.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleStringProperty(d.getValue().getCategorie()));

        TableColumn<Article, Number> c6 = new TableColumn<>("Stock");
        c6.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getQtestk()));

        TableColumn<Article, Number> c7 = new TableColumn<>("TVA");
        c7.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getCodetva()));

        table.getColumns().addAll(c1,c2,c3,c4,c5,c6,c7);
        return table;
    }

    private void charger() {
        data = FXCollections.observableArrayList();
        try {
            data.addAll(new ArticleController().getArticles());
            table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
