package views;

import Controleur.LivraisonController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import comgestion.model.Livraison;

public class VueLivraisons extends BorderPane {

    private TableView<Livraison> table;

    public VueLivraisons() {
        setPadding(new Insets(10));
        setTop(new Label("Gestion Livraisons"));
        setCenter(tableau());
        charger();
    }

    private TableView<Livraison> tableau() {
        table = new TableView<>();

        TableColumn<Livraison, Number> c1 = new TableColumn<>("Commande");
        c1.setCellValueFactory(d ->
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getNocde()));

        TableColumn<Livraison, String> c2 = new TableColumn<>("Date");
        c2.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                d.getValue().getDateliv().toString()));

        TableColumn<Livraison, Number> c3 = new TableColumn<>("Livreur");
        c3.setCellValueFactory(d ->
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getLivreur()));

        TableColumn<Livraison, String> c4 = new TableColumn<>("Paiement");
        c4.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(d.getValue().getModepay()));

        TableColumn<Livraison, String> c5 = new TableColumn<>("Etat");
        c5.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(d.getValue().getEtatliv()));

        table.getColumns().addAll(c1,c2,c3,c4,c5);
        return table;
    }

    private void charger() {
        try {
            table.setItems(FXCollections.observableArrayList(
                new LivraisonController().getLivraisons()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
