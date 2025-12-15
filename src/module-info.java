module GestionCommerciale {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens comgestion to javafx.fxml;
    opens Views to javafx.fxml;
    opens comgestion.model to javafx.base;
    opens Controleur to javafx.fxml;
    
    exports comgestion;
    exports Views;
    exports comgestion.model;
    exports Controleur;
}