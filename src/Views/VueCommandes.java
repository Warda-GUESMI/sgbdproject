package Views;

import Controleur.CommandeController;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import comgestion.model.Commande;
import javafx.geometry.Insets;

public class VueCommandes extends BorderPane {

    private TableView<Commande> table;
    private Button btnRafraichir;

    public VueCommandes() {
        setPadding(new Insets(10));
        setTop(entete());
        setCenter(tableau());
        charger();
    }
    
    private VBox entete() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        
        Label titre = new Label("🛒 Gestion des Commandes");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        btnRafraichir = new Button("🔄 Rafraîchir");
        btnRafraichir.setOnAction(e -> charger());
        
        HBox hbox = new HBox(10, btnRafraichir);
        
        box.getChildren().addAll(titre, new Separator(), hbox);
        return box;
    }

    private TableView<Commande> tableau() {
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Commande, Number> c1 = new TableColumn<>("N° Commande");
        c1.setCellValueFactory(d ->
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getNocde()));
        c1.setPrefWidth(120);

        TableColumn<Commande, Number> c2 = new TableColumn<>("N° Client");
        c2.setCellValueFactory(d ->
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getNoclt()));
        c2.setPrefWidth(100);

        TableColumn<Commande, String> c3 = new TableColumn<>("Date");
        c3.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(
                d.getValue().getDatecde().toString()));
        c3.setPrefWidth(120);

        TableColumn<Commande, String> c4 = new TableColumn<>("État");
        c4.setCellValueFactory(d -> {
            String etat = d.getValue().getEtatcde();
            String libelle = decodeEtat(etat);
            return new javafx.beans.property.SimpleStringProperty(libelle + " (" + etat + ")");
        });
        c4.setPrefWidth(150);

        table.getColumns().addAll(c1, c2, c3, c4);
        
        // Style des lignes selon l'état
        table.setRowFactory(tv -> new TableRow<Commande>() {
            @Override
            protected void updateItem(Commande item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    switch (item.getEtatcde()) {
                        case "EC":
                            setStyle("-fx-background-color: #fff3cd;"); // Jaune pâle
                            break;
                        case "PR":
                            setStyle("-fx-background-color: #d1ecf1;"); // Bleu pâle
                            break;
                        case "LI":
                            setStyle("-fx-background-color: #d4edda;"); // Vert pâle
                            break;
                        case "SO":
                            setStyle("-fx-background-color: #e2e3e5;"); // Gris
                            break;
                        default:
                            setStyle("");
                    }
                }
            }
        });
        
        return table;
    }

    private void charger() {
        try {
            table.setItems(FXCollections.observableArrayList(
                new CommandeController().getCommandes()));
            System.out.println("✅ Commandes chargées");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de chargement");
            alert.setContentText("Impossible de charger les commandes : " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    /**
     * Décode l'état de la commande
     */
    private String decodeEtat(String etat) {
        if (etat == null) return "Inconnu";
        switch (etat.trim()) {
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