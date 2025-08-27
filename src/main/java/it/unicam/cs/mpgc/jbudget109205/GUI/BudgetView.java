package it.unicam.cs.mpgc.jbudget109205.GUI;

import it.unicam.cs.mpgc.jbudget109205.Model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class BudgetView {

    private final BudgetManager budgetManager;
    private final CategoryManager categoryManager;
    private final TableView<Budget> table;

    public BudgetView(BudgetManager budgetManager, CategoryManager categoryManager) {
        this.budgetManager = budgetManager;
        this.categoryManager = categoryManager;
        this.table = new TableView<>();
    }

    public VBox getView() {
        // Colonne
        TableColumn<Budget, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getCategory().getName())
        );

        TableColumn<Budget, Double> colTotale = new TableColumn<>("Totale");
        colTotale.setCellValueFactory(cd ->
                new SimpleDoubleProperty(cd.getValue().getLimit()).asObject()
        );

        TableColumn<Budget, Double> colUsato = new TableColumn<>("Usato");
        colUsato.setCellValueFactory(cd -> {
            Budget budget = cd.getValue();
            double speso = budgetManager.getTotalSpent(budget.getCategory(), budget.getMonth());
            return new SimpleDoubleProperty(speso).asObject();
        });

        TableColumn<Budget, YearMonth> colScadenza = new TableColumn<>("Mese");
        colScadenza.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getMonth())
        );

        table.getColumns().addAll(colCategoria, colTotale, colUsato, colScadenza);

        // --- Input ---
        ComboBox<ICategory> categoriaBox = new ComboBox<>();
        categoriaBox.getItems().setAll(new ArrayList<>(categoryManager.getAllCategories()));
        categoriaBox.setPromptText("Categoria");

        TextField totaleField = new TextField();
        totaleField.setPromptText("Importo Totale");

        DatePicker dataPicker = new DatePicker(LocalDate.now().withDayOfMonth(1));

        Button aggiungiBtn = new Button("Aggiungi Budget");
        aggiungiBtn.setOnAction(e -> {
            try {
                ICategory categoria = categoriaBox.getValue();
                double totale = Double.parseDouble(totaleField.getText().trim());
                LocalDate data = dataPicker.getValue();

                if (categoria == null || data == null) {
                    throw new IllegalArgumentException("Seleziona categoria e data.");
                }

                YearMonth mese = YearMonth.from(data);
                budgetManager.setBudget(categoria, mese, totale);
                refresh();

                categoriaBox.setValue(null);
                totaleField.clear();
                dataPicker.setValue(LocalDate.now().withDayOfMonth(1));

            } catch (NumberFormatException nfe) {
                new Alert(Alert.AlertType.ERROR, "Importo non valido.").showAndWait();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            }
        });

        VBox layout = new VBox(10,
                table,
                categoriaBox,
                totaleField,
                dataPicker,
                aggiungiBtn
        );
        layout.setPadding(new Insets(10));

        return layout;
    }

    public void refresh() {
        table.getItems().setAll(budgetManager.getAllBudgetEntries());
    }
}