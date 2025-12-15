package Views;

import Controleur.LivraisonController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import comgestion.model.Livraison;
import java.time.format.DateTimeFormatter;

public class VueLivraisons extends BorderPane {

    private TableView<Livraison> table;
    private Button btnRafraichir;
    private Label lblInfo;
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VueLivraisons() {
        setPadding(new Insets(10));
        setTop(entete());
        setCenter(tableau());
        charger();
    }
    
    private VBox entete() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        
        Label titre = new Label("🚚 Gestion des Livraisons");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        lblInfo = new Label("");
        lblInfo.setStyle("-fx-text-fill: #7f8c8d;");
        
        btnRafraichir = new Button("🔄 Rafraîchir");
        btnRafraichir.setOnAction(e -> charger());
        
        HBox hbox = new HBox(10, btnRafraichir);
        
        box.getChildren().addAll(titre, lblInfo, new Separator(), hbox);
        return box;
    }

    private TableView<Livraison> tableau() {
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Aucune livraison à afficher"));

        TableColumn<Livraison, Number> c1 = new TableColumn<>("N° Commande");
        c1.setCellValueFactory(d ->
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getNocde()));
        c1.setPrefWidth(120);

        TableColumn<Livraison, String> c2 = new TableColumn<>("Date Livraison");
        c2.setCellValueFactory(d -> {
            try {
                String dateStr = d.getValue().getDateliv().format(DATE_FORMATTER);
                return new javafx.beans.property.SimpleStringProperty(dateStr);
            } catch (Exception e) {
                return new javafx.beans.property.SimpleStringProperty("Date invalide");
            }
        });
        c2.setPrefWidth(150);

        TableColumn<Livraison, Number> c3 = new TableColumn<>("N° Livreur");
        c3.setCellValueFactory(d ->
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getLivreur()));
        c3.setPrefWidth(100);

        TableColumn<Livraison, String> c4 = new TableColumn<>("Mode Paiement");
        c4.setCellValueFactory(d -> {
            String mode = d.getValue().getModepay();
            String libelle = decodeModePay(mode);
            return new javafx.beans.property.SimpleStringProperty(libelle);
        });
        c4.setPrefWidth(150);

        TableColumn<Livraison, String> c5 = new TableColumn<>("État");
        c5.setCellValueFactory(d -> {
            String etat = d.getValue().getEtatliv();
            String libelle = decodeEtat(etat);
            return new javafx.beans.property.SimpleStringProperty(libelle + " (" + etat + ")");
        });
        c5.setPrefWidth(120);

        table.getColumns().addAll(c1, c2, c3, c4, c5);
        
        // Style des lignes selon l'état
        table.setRowFactory(tv -> new TableRow<Livraison>() {
            @Override
            protected void updateItem(Livraison item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    String etat = item.getEtatliv() != null ? item.getEtatliv().trim() : "";
                    switch (etat) {
                        case "EC":
                            setStyle("-fx-background-color: #fff3cd;"); // Jaune
                            break;
                        case "LI":
                            setStyle("-fx-background-color: #d4edda;"); // Vert
                            break;
                        case "AL":
                            setStyle("-fx-background-color: #f8d7da;"); // Rouge pâle
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
            LivraisonController controller = new LivraisonController();
            var livraisons = controller.getLivraisons();
            table.setItems(FXCollections.observableArrayList(livraisons));
            
            lblInfo.setText("✅ " + livraisons.size() + " livraison(s) chargée(s)");
            lblInfo.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            
            System.out.println("✅ " + livraisons.size() + " livraisons chargées dans la vue");
            
        } catch (Exception e) {
            e.printStackTrace();
            lblInfo.setText("❌ Erreur de chargement");
            lblInfo.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de chargement des livraisons");
            alert.setContentText("Détails : " + e.getMessage() + 
                "\n\nVérifiez :\n" +
                "- Que la table LivraisonCom existe\n" +
                "- Que les colonnes sont correctes\n" +
                "- Que vous avez des données dans la table");
            alert.showAndWait();
        }
    }
    
    /**
     * Décode l'état de la livraison
     */
    private String decodeEtat(String etat) {
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
    private String decodeModePay(String mode) {
        if (mode == null) return "Inconnu";
        switch (mode.trim()) {
            case "avant_livraison": return "Avant livraison";
            case "apres_livraison": return "Après livraison";
            default: return mode;
        }
    }
}