package Views;

import Controleur.ArticleController;
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
    private Button btnAjouter, btnModifier, btnSupprimer, btnRafraichir;

    public VueArticles() {
        setPadding(new Insets(10));
        setTop(formulaire());
        setCenter(tableau());
        charger();
    }

    private VBox formulaire() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-width: 1;");

        Label titre = new Label("📦 Gestion des Articles");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        txtRef = new TextField();
        txtRef.setPromptText("Ex: A1");
        txtRef.setMaxWidth(100);
        
        txtDes = new TextField();
        txtDes.setPromptText("Désignation de l'article");
        txtDes.setPrefWidth(250);
        
        txtPrixA = new TextField();
        txtPrixA.setPromptText("0.00");
        txtPrixA.setMaxWidth(100);
        
        txtPrixV = new TextField();
        txtPrixV.setPromptText("0.00");
        txtPrixV.setMaxWidth(100);
        
        txtStock = new TextField();
        txtStock.setPromptText("0");
        txtStock.setMaxWidth(100);
        
        txtTVA = new TextField();
        txtTVA.setText("1");
        txtTVA.setMaxWidth(80);

        cmbCat = new ComboBox<>();
        cmbCat.getItems().addAll("Info", "Audio", "Telephone", "Tablette");
        cmbCat.setPromptText("Choisir catégorie");

        GridPane g = new GridPane();
        g.setHgap(15);
        g.setVgap(10);

        g.addRow(0, new Label("Référence:"), txtRef,
                    new Label("Désignation:"), txtDes);
        g.addRow(1, new Label("Prix Achat:"), txtPrixA,
                    new Label("Prix Vente:"), txtPrixV);
        g.addRow(2, new Label("Stock:"), txtStock,
                    new Label("TVA:"), txtTVA);
        g.addRow(3, new Label("Catégorie:"), cmbCat);

        // Boutons
        btnAjouter = new Button("➕ Ajouter");
        btnAjouter.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        btnAjouter.setOnAction(e -> ajouterArticle());
        
        btnModifier = new Button("✏️ Modifier");
        btnModifier.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        btnModifier.setOnAction(e -> modifierArticle());
        
        btnSupprimer = new Button("🗑️ Supprimer");
        btnSupprimer.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnSupprimer.setOnAction(e -> supprimerArticle());
        
        btnRafraichir = new Button("🔄 Rafraîchir");
        btnRafraichir.setOnAction(e -> charger());

        HBox btns = new HBox(10, btnAjouter, btnModifier, btnSupprimer, btnRafraichir);
        btns.setPadding(new Insets(10, 0, 0, 0));

        box.getChildren().addAll(titre, new Separator(), g, btns);
        return box;
    }

    private TableView<Article> tableau() {
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Article, String> c1 = new TableColumn<>("Référence");
        c1.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleStringProperty(d.getValue().getRefart()));
        c1.setPrefWidth(80);

        TableColumn<Article, String> c2 = new TableColumn<>("Désignation");
        c2.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleStringProperty(d.getValue().getDesignation()));
        c2.setPrefWidth(200);

        TableColumn<Article, Number> c3 = new TableColumn<>("Prix Achat");
        c3.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleDoubleProperty(d.getValue().getPrixA()));
        c3.setPrefWidth(100);

        TableColumn<Article, Number> c4 = new TableColumn<>("Prix Vente");
        c4.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleDoubleProperty(d.getValue().getPrixV()));
        c4.setPrefWidth(100);

        TableColumn<Article, String> c5 = new TableColumn<>("Catégorie");
        c5.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleStringProperty(d.getValue().getCategorie()));
        c5.setPrefWidth(100);

        TableColumn<Article, Number> c6 = new TableColumn<>("Stock");
        c6.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getQtestk()));
        c6.setPrefWidth(80);

        TableColumn<Article, Number> c7 = new TableColumn<>("TVA");
        c7.setCellValueFactory(d -> 
            new javafx.beans.property.SimpleIntegerProperty(d.getValue().getCodetva()));
        c7.setPrefWidth(60);

        table.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7);
        
        // Selection
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                remplirFormulaire(newVal);
            }
        });
        
        return table;
    }

    private void charger() {
        data = FXCollections.observableArrayList();
        try {
            data.addAll(new ArticleController().getArticles());
            table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            afficherErreur("Erreur de chargement", 
                "Impossible de charger les articles : " + e.getMessage());
        }
    }
    
    private void remplirFormulaire(Article a) {
        txtRef.setText(a.getRefart());
        txtDes.setText(a.getDesignation());
        txtPrixA.setText(String.valueOf(a.getPrixA()));
        txtPrixV.setText(String.valueOf(a.getPrixV()));
        txtStock.setText(String.valueOf(a.getQtestk()));
        txtTVA.setText(String.valueOf(a.getCodetva()));
        cmbCat.setValue(a.getCategorie());
    }
    
    private void viderFormulaire() {
        txtRef.clear();
        txtDes.clear();
        txtPrixA.clear();
        txtPrixV.clear();
        txtStock.clear();
        txtTVA.setText("1");
        cmbCat.setValue(null);
    }
    
    private void ajouterArticle() {
        try {
            Article a = new Article(
                txtRef.getText().trim(),
                txtDes.getText().trim(),
                Double.parseDouble(txtPrixA.getText()),
                Double.parseDouble(txtPrixV.getText()),
                cmbCat.getValue(),
                Integer.parseInt(txtTVA.getText()),
                Integer.parseInt(txtStock.getText())
            );
            
            new ArticleController().ajouterArticle(a);
            afficherSucces("Article ajouté avec succès !");
            viderFormulaire();
            charger();
            
        } catch (NumberFormatException e) {
            afficherErreur("Erreur de saisie", "Vérifiez les valeurs numériques");
        } catch (Exception e) {
            afficherErreur("Erreur", e.getMessage());
        }
    }
    
    private void modifierArticle() {
        Article selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            afficherErreur("Aucune sélection", "Veuillez sélectionner un article");
            return;
        }
        
        try {
            Article a = new Article(
                txtRef.getText().trim(),
                txtDes.getText().trim(),
                Double.parseDouble(txtPrixA.getText()),
                Double.parseDouble(txtPrixV.getText()),
                cmbCat.getValue(),
                Integer.parseInt(txtTVA.getText()),
                Integer.parseInt(txtStock.getText())
            );
            
            new ArticleController().modifierArticle(a);
            afficherSucces("Article modifié avec succès !");
            charger();
            
        } catch (Exception e) {
            afficherErreur("Erreur", e.getMessage());
        }
    }
    
    private void supprimerArticle() {
        Article selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            afficherErreur("Aucune sélection", "Veuillez sélectionner un article");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer l'article ?");
        confirm.setContentText("Article: " + selected.getRefart() + " - " + selected.getDesignation());
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    new ArticleController().supprimerArticle(selected.getRefart());
                    afficherSucces("Article supprimé avec succès !");
                    viderFormulaire();
                    charger();
                } catch (Exception e) {
                    afficherErreur("Erreur", e.getMessage());
                }
            }
        });
    }
    
    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void afficherSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}