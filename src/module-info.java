/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */


module GestionCommerciale {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens comgestion to javafx.fxml;
    opens Views to javafx.fxml;
    opens comgestion.model to javafx.base;
    
    exports comgestion;
    exports Views;
    exports comgestion.model;
    opens Controleur to javafx.fxml;

            
}