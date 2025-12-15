package comgestion;

import Views.VueArticles;
import Views.VueCommandes;
import Views.VueLivraisons;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;

    // 🔹 VUES (on ne les modifie PAS)
    private VueArticles vueArticle;
    private VueCommandes vueCommandes;
    private VueLivraisons vueLivraison;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // 🔴 TEST CONNEXION ORACLE
        if (!testConnexionBD()) {
            showErreurBD();
            return;
        }

        primaryStage.setTitle("Système de Gestion Commerciale");

        // Initialisation layout
        mainLayout = new BorderPane();
        mainLayout.setTop(createMenuBar());
        mainLayout.setCenter(createWelcomeScreen());

        // Initialisation des vues UNE FOIS
        vueArticle = new VueArticle();
        vueCommandes = new VueCommandes();
        vueLivraison = new VueLivraison();

        Scene scene = new Scene(mainLayout, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ================== CONNEXION BD ==================

    private boolean testConnexionBD() {
        try {
            Connection c = ConnexionBD.getConnection();
            return c != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showErreurBD() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText("Connexion à la base de données impossible");
        alert.setContentText(
                "✔ Oracle démarré\n" +
                "✔ Listener actif\n" +
                "✔ Service XE/PDB correct\n" +
                "✔ Login / mot de passe corrects"
        );
        alert.showAndWait();
    }

    // ================== MENU ==================

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu menuGestion = new Menu("Gestion");

        MenuItem menuArticles = new MenuItem("Articles");
        MenuItem menuCommandes = new MenuItem("Commandes");
        MenuItem menuLivraisons = new MenuItem("Livraisons");
        MenuItem menuQuitter = new MenuItem("Quitter");

        menuArticles.setOnAction(e -> showArticlesView());
        menuCommandes.setOnAction(e -> showCommandesView());
        menuLivraisons.setOnAction(e -> showLivraisonsView());
        menuQuitter.setOnAction(e -> primaryStage.close());

        menuGestion.getItems().addAll(
                menuArticles,
                menuCommandes,
                menuLivraisons,
                new SeparatorMenuItem(),
                menuQuitter
        );

        menuBar.getMenus().add(menuGestion);
        return menuBar;
    }

    // ================== ACCUEIL ==================

    private VBox createWelcomeScreen() {
        VBox welcome = new VBox(25);
        welcome.setAlignment(Pos.CENTER);
        welcome.setPadding(new Insets(50));

        Label title = new Label("Système de Gestion Commerciale");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Label subtitle = new Label("Articles • Commandes • Livraisons");
        subtitle.setStyle("-fx-font-size: 16px;");

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);

        Button btnArticles = createButton("📦 Articles", this::showArticlesView);
        Button btnCommandes = createButton("🛒 Commandes", this::showCommandesView);
        Button btnLivraisons = createButton("🚚 Livraisons", this::showLivraisonsView);

        buttons.getChildren().addAll(btnArticles, btnCommandes, btnLivraisons);

        welcome.getChildren().addAll(title, subtitle, buttons);
        return welcome;
    }

    private Button createButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.setPrefSize(200, 120);
        btn.setStyle("-fx-font-size: 14px;");
        btn.setOnAction(e -> action.run());
        return btn;
    }

    // ================== NAVIGATION ==================

    private void showArticlesView() {
        mainLayout.setCenter(vueArticle.getView());
    }

    private void showCommandesView() {
        mainLayout.setCenter(vueCommandes.getView());
    }

    private void showLivraisonsView() {
        mainLayout.setCenter(vueLivraison.getView());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

