package it.unicam.cs.mpgc.jbudget109205.GUI;

import it.unicam.cs.mpgc.jbudget109205.Model.*;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.time.LocalDate;
import java.time.YearMonth;

public class MainApp extends Application {

    private MovimentoManager movimentoManager;
    private BudgetManager budgetManager;
    private CategoryManager categoryManager;
    private Scadenzario scadenzario;
    private Statistiche statistiche;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("jBudget 109205");

        movimentoManager = new MovimentoManager();
        budgetManager = new BudgetManager(movimentoManager);
        categoryManager = new CategoryManager();
        scadenzario = new Scadenzario(movimentoManager);
        statistiche = new Statistiche(movimentoManager);


        BorderPane root = new BorderPane();


        TabPane tabPane = new TabPane();

        MovimentiView movimentiView = new MovimentiView(movimentoManager, categoryManager);
        Tab movimentiTab = new Tab("Movimenti", movimentiView.getView());
        movimentiTab.setClosable(false);


        BudgetView budgetView = new BudgetView(budgetManager, categoryManager);
        Tab budgetTab = new Tab("Budget", budgetView.getView());
        budgetTab.setClosable(false);


        ScadenzarioView scadenzarioView = new ScadenzarioView(scadenzario, categoryManager);
        Tab scadenzarioTab = new Tab("Scadenzario", scadenzarioView.getView());
        scadenzarioTab.setClosable(false);


        StatisticheView statisticheView = new StatisticheView(statistiche);
        Tab statisticheTab = new Tab("Statistiche", statisticheView.getView());
        statisticheTab.setClosable(false);


        tabPane.getTabs().addAll(movimentiTab, budgetTab, scadenzarioTab, statisticheTab);

        root.setCenter(tabPane);


        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}