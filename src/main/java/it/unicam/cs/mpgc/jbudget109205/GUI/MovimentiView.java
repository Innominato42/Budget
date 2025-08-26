package it.unicam.cs.mpgc.jbudget109205.GUI;

import it.unicam.cs.mpgc.jbudget109205.Model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class MovimentiView {
    private final MovimentoManager movimentoManager;
    private final BudgetManager budgetManager;
    private final TableView<Movimento> movimentiTable;

    // riferimento alla budgetView per notificare aggiornamenti
    private BudgetView budgetView;

    public MovimentiView(MovimentoManager movimentoManager, BudgetManager budgetManager) {
        this.movimentoManager = movimentoManager;
        this.budgetManager = budgetManager;
        this.movimentiTable = new TableView<>();
    }

    public void setBudgetView(BudgetView budgetView) {
        this.budgetView = budgetView;
    }

    public VBox getView() {
        // Colonne tabella
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

        // Campi input
        TextField descrizioneField = new TextField();
        descrizioneField.setPromptText("Descrizione");

        TextField importoField = new TextField();
        importoField.setPromptText("Importo");

        DatePicker dataPicker = new DatePicker(LocalDate.now());

        TextField categoriaField = new TextField();
        categoriaField.setPromptText("Categoria");

        Button aggiungiBtn = new Button("Aggiungi Movimento");
        aggiungiBtn.setOnAction(e -> {
            try {
                double importo = Double.parseDouble(importoField.getText());
                String descrizione = descrizioneField.getText();
                LocalDate data = dataPicker.getValue();
                String categoriaNome = categoriaField.getText();

                Movimento nuovo = new Movimento(importo, data, descrizione);
                nuovo.aggiungiCategoria(new Category(categoriaNome, null));

                movimentoManager.aggiungiMovimento(nuovo);
                movimentiTable.getItems().add(nuovo);

                // aggiorno la budgetView se esiste
                if (budgetView != null) {
                    budgetView.refresh();
                }

                // pulizia campi
                importoField.clear();
                descrizioneField.clear();
                categoriaField.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Importo non valido", ButtonType.OK);
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10,
                movimentiTable,
                descrizioneField,
                importoField,
                dataPicker,
                categoriaField,
                aggiungiBtn
        );
        layout.setPadding(new Insets(10));

        return layout;
    }
}
