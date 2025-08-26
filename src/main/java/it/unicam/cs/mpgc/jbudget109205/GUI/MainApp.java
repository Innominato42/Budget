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

    @Override
    public void start(Stage primaryStage) {
        movimentoManager = new MovimentoManager();
        budgetManager = new BudgetManager(movimentoManager);

        MovimentiView movimentiView = new MovimentiView(movimentoManager, budgetManager);
        BudgetView budgetView = new BudgetView(budgetManager);

        // collego le due view
        movimentiView.setBudgetView(budgetView);

        TabPane tabPane = new TabPane();

        Tab movimentiTab = new Tab("Movimenti", movimentiView.getView());
        movimentiTab.setClosable(false);

        Tab budgetTab = new Tab("Budget", budgetView.getView());
        budgetTab.setClosable(false);

        tabPane.getTabs().addAll(movimentiTab, budgetTab);

        Scene scene = new Scene(tabPane, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("jBudget 109205");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}