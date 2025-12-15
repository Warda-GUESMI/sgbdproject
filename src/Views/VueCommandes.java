package views;

import Controleur.CommandeController;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import comgestion.model.Commande;
import javafx.geometry.Insets;

public class VueCommandes extends BorderPane {

    private TableView<Commande> table;

    public VueCommandes() {
        setPadding(new Insets(10));
        setTop(new Label("Gestion Commandes"));
        setCenter(tableau());
        charger();
    }

    private TableView<Commande> tableau() {
        table = new TableView<>();

        TableColumn<Commande, Number> c1 = new TableColumn<>("N°");
        c1.setCellValueFactory(d ->
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getNocde()));

        TableColumn<Commande, Number> c2 = new TableColumn<>("Client");
        c2.setCellValueFactory(d ->
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getNoclt()));

        TableColumn<Commande, String> c3 = new TableColumn<>("Date");
        c3.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                d.getValue().getDatecde().toString()));

        TableColumn<Commande, String> c4 = new TableColumn<>("Etat");
        c4.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(d.getValue().getEtatcde()));

        table.getColumns().addAll(c1,c2,c3,c4);
        return table;
    }

    private void charger() {
        try {
            table.setItems(FXCollections.observableArrayList(
                new CommandeController().getCommandes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
