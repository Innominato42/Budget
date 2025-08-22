package it.unicam.cs.mpgc.jbudget109205.GUI;

import it.unicam.cs.mpgc.jbudget109205.Model.BudgetManager;
import it.unicam.cs.mpgc.jbudget109205.Model.Category;
import it.unicam.cs.mpgc.jbudget109205.Model.Movimento;
import it.unicam.cs.mpgc.jbudget109205.Model.MovimentoManager;
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
import it.unicam.cs.mpgc.jbudget109205.Model.ICategory;


import java.time.LocalDate;

public class MainApp extends Application {

    private MovimentoManager movimentoManager;
    private BudgetManager budgetManager;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("jBudget 109205");

        movimentoManager = new MovimentoManager();
        budgetManager = new BudgetManager(movimentoManager);

        // Layout principale
        BorderPane root = new BorderPane();

        // TabPane con le sezioni
        TabPane tabPane = new TabPane();

        // --- TAB MOVIMENTI ---
        Tab movimentiTab = new Tab("Movimenti");
        movimentiTab.setClosable(false);

        // Tabella movimenti
        TableView<Movimento> movimentiTable = new TableView<>();

        TableColumn<Movimento, Double> colImporto = new TableColumn<>("Importo");
        colImporto.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());

        TableColumn<Movimento, String> colDescrizione = new TableColumn<>("Descrizione");
        colDescrizione.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        TableColumn<Movimento, LocalDate> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));

        TableColumn<Movimento, String> colCategorie = new TableColumn<>("Categorie");
        colCategorie.setCellValueFactory(cellData -> {
            String categorie = cellData.getValue().getCategories().stream()
                    .map(ICategory::getName)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            return new SimpleStringProperty(categorie);
        });

        movimentiTable.getColumns().addAll(colImporto, colDescrizione, colData, colCategorie);

        // Pulsante aggiunta
        Button aggiungiBtn = new Button("Aggiungi Movimento");
        aggiungiBtn.setOnAction(e -> {
            Movimento nuovo = new Movimento(-50.0, LocalDate.now(), "Spesa test");
            nuovo.aggiungiCategoria(new Category("Test", null));
            movimentoManager.aggiungiMovimento(nuovo);
            movimentiTable.getItems().add(nuovo);
        });

        VBox movimentiLayout = new VBox(10, movimentiTable, aggiungiBtn);
        movimentiLayout.setPadding(new Insets(10));

        movimentiTab.setContent(movimentiLayout);
        // --- FINE TAB MOVIMENTI ---

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