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
        primaryStage.setTitle("jBudget 109205");

        movimentoManager = new MovimentoManager();
        budgetManager = new BudgetManager(movimentoManager);

        // Layout principale
        BorderPane root = new BorderPane();
        TabPane tabPane = new TabPane();

        // ------------------ TAB MOVIMENTI ------------------
        Tab movimentiTab = new Tab("Movimenti");
        movimentiTab.setClosable(false);

        TableView<Movimento> movimentiTable = new TableView<>();

        TableColumn<Movimento, Double> colImporto = new TableColumn<>("Importo");
        colImporto.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().getAmount()).asObject());

        TableColumn<Movimento, String> colDescrizione = new TableColumn<>("Descrizione");
        colDescrizione.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getDescription()));

        TableColumn<Movimento, LocalDate> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getDate()));

        TableColumn<Movimento, String> colCategorie = new TableColumn<>("Categorie");
        colCategorie.setCellValueFactory(cd -> {
            String categorie = cd.getValue().getCategories().stream()
                    .map(ICategory::getName)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            return new SimpleStringProperty(categorie);
        });

        movimentiTable.getColumns().addAll(colImporto, colDescrizione, colData, colCategorie);

        TextField importoField = new TextField();
        importoField.setPromptText("Importo");

        TextField descrizioneField = new TextField();
        descrizioneField.setPromptText("Descrizione");

        DatePicker dataPicker = new DatePicker(LocalDate.now());

        TextField categoriaField = new TextField();
        categoriaField.setPromptText("Categoria");

        Button aggiungiMovBtn = new Button("Aggiungi Movimento");
        aggiungiMovBtn.setOnAction(e -> {
            try {
                double importo = Double.parseDouble(importoField.getText());
                String descrizione = descrizioneField.getText();
                LocalDate data = dataPicker.getValue();
                String categoriaNome = categoriaField.getText();

                Movimento nuovo = new Movimento(importo, data, descrizione);
                if (categoriaNome != null && !categoriaNome.isBlank()) {
                    nuovo.aggiungiCategoria(new Category(categoriaNome, null));
                }

                movimentoManager.aggiungiMovimento(nuovo);
                movimentiTable.getItems().add(nuovo);

                importoField.clear();
                descrizioneField.clear();
                categoriaField.clear();
                dataPicker.setValue(LocalDate.now());

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inserisci un importo valido!", ButtonType.OK);
                alert.showAndWait();
            }
        });

        VBox movimentiLayout = new VBox(10, movimentiTable, importoField, descrizioneField, dataPicker, categoriaField, aggiungiMovBtn);
        movimentiLayout.setPadding(new Insets(10));
        movimentiTab.setContent(movimentiLayout);

        // ------------------ TAB BUDGET ------------------
        Tab budgetTab = new Tab("Budget");
        budgetTab.setClosable(false);

        TableView<Budget> budgetTable = new TableView<>();

        TableColumn<Budget, String> colCatBudget = new TableColumn<>("Categoria");
        colCatBudget.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getCategory().getName()));

        TableColumn<Budget, String> colMese = new TableColumn<>("Mese");
        colMese.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getMonth().toString()));

        TableColumn<Budget, Double> colImportoBudget = new TableColumn<>("Importo");
        colImportoBudget.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().getLimit()).asObject());

        budgetTable.getColumns().addAll(colCatBudget, colMese, colImportoBudget);

        TextField categoriaBudgetField = new TextField();
        categoriaBudgetField.setPromptText("Categoria");

        DatePicker mesePicker = new DatePicker(LocalDate.now());

        TextField importoBudgetField = new TextField();
        importoBudgetField.setPromptText("Importo Budget");

        Button aggiungiBudgetBtn = new Button("Aggiungi Budget");
        aggiungiBudgetBtn.setOnAction(e -> {
            try {
                String catNome = categoriaBudgetField.getText();
                YearMonth mese = YearMonth.from(mesePicker.getValue());
                double importo = Double.parseDouble(importoBudgetField.getText());

                Category categoria = new Category(catNome, null);
                budgetManager.setBudget(categoria, mese, importo);

                budgetTable.getItems().setAll(budgetManager.getAllBudgetEntries());

                categoriaBudgetField.clear();
                importoBudgetField.clear();
                mesePicker.setValue(LocalDate.now());

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Errore nell'inserimento del budget!", ButtonType.OK);
                alert.showAndWait();
            }
        });

        VBox budgetLayout = new VBox(10, budgetTable, categoriaBudgetField, mesePicker, importoBudgetField, aggiungiBudgetBtn);
        budgetLayout.setPadding(new Insets(10));
        budgetTab.setContent(budgetLayout);

        // ------------------ ALTRE TAB ------------------
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