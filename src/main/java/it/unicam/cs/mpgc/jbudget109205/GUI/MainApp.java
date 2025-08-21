package it.unicam.cs.mpgc.jbudget109205.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("jBudget 109205");

        // Layout principale
        BorderPane root = new BorderPane();

        // TabPane con le sezioni
        TabPane tabPane = new TabPane();

        Tab movimentiTab = new Tab("Movimenti");
        movimentiTab.setClosable(false);

        Tab budgetTab = new Tab("Budget");
        budgetTab.setClosable(false);

        Tab scadenzarioTab = new Tab("Scadenzario");
        scadenzarioTab.setClosable(false);

        Tab statisticheTab = new Tab("Statistiche");
        statisticheTab.setClosable(false);

        // Aggiungo tutte le tab
        tabPane.getTabs().addAll(movimentiTab, budgetTab, scadenzarioTab, statisticheTab);

        root.setCenter(tabPane);

        // Creo scena e mostro
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}