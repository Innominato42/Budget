package it.unicam.cs.mpgc.jbudget109205.GUI;

import it.unicam.cs.mpgc.jbudget109205.Model.Budget;
import it.unicam.cs.mpgc.jbudget109205.Model.BudgetManager;
import it.unicam.cs.mpgc.jbudget109205.Model.Category;
import it.unicam.cs.mpgc.jbudget109205.Model.ICategory;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.YearMonth;

public class BudgetView {

    private final BudgetManager budgetManager;
    private final TableView<Budget> budgetTable;

    public BudgetView(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
        this.budgetTable = new TableView<>();
    }

    public VBox getView() {
        // Colonne tabella
        TableColumn<Budget, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getCategory().getName()));

        TableColumn<Budget, YearMonth> colMese = new TableColumn<>("Mese");
        colMese.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getMonth()));

        TableColumn<Budget, Double> colLimite = new TableColumn<>("Budget");
        colLimite.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().getLimit()).asObject());

        TableColumn<Budget, Double> colSpeso = new TableColumn<>("Speso");
        colSpeso.setCellValueFactory(cd ->
                new SimpleDoubleProperty(
                        budgetManager.getTotalSpent(cd.getValue().getCategory(), cd.getValue().getMonth())
                ).asObject()
        );

        TableColumn<Budget, Double> colRimanente = new TableColumn<>("Rimanente");
        colRimanente.setCellValueFactory(cd ->
                new SimpleDoubleProperty(
                        budgetManager.getRemainingBudget(cd.getValue().getCategory(), cd.getValue().getMonth())
                ).asObject()
        );

        budgetTable.getColumns().addAll(colCategoria, colMese, colLimite, colSpeso, colRimanente);

        // Campi input
        TextField categoriaField = new TextField();
        categoriaField.setPromptText("Categoria");

        TextField meseField = new TextField();
        meseField.setPromptText("Mese (YYYY-MM)");

        TextField budgetField = new TextField();
        budgetField.setPromptText("Budget");

        Button aggiungiBtn = new Button("Aggiungi Budget");
        aggiungiBtn.setOnAction(e -> {
            try {
                String categoriaNome = categoriaField.getText();
                YearMonth mese = YearMonth.parse(meseField.getText());
                double importo = Double.parseDouble(budgetField.getText());

                ICategory categoria = new Category(categoriaNome, null);
                budgetManager.setBudget(categoria, mese, importo);

                refresh();

                categoriaField.clear();
                meseField.clear();
                budgetField.clear();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Dati non validi", ButtonType.OK);
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10,
                budgetTable,
                categoriaField,
                meseField,
                budgetField,
                aggiungiBtn
        );
        layout.setPadding(new Insets(10));

        return layout;
    }

    public void refresh() {
        budgetTable.getItems().setAll(budgetManager.getAllBudgetEntries());
    }
}
