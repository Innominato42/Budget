package it.unicam.cs.mpgc.jbudget109205.GUI;

import it.unicam.cs.mpgc.jbudget109205.Model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

    public class MovimentiView {

        private final MovimentoManager movimentoManager;
        private final CategoryManager categoryManager;
        private final TableView<Movimento> table;

        public MovimentiView(MovimentoManager movimentoManager, CategoryManager categoryManager) {
            this.movimentoManager = movimentoManager;
            this.categoryManager = categoryManager;
            this.table = new TableView<>();
        }

        public VBox getView() {

            TableColumn<Movimento, Double> colImporto = new TableColumn<>("Importo");
            colImporto.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().getAmount()).asObject());

            TableColumn<Movimento, String> colDescrizione = new TableColumn<>("Descrizione");
            colDescrizione.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getDescription()));

            TableColumn<Movimento, LocalDate> colData = new TableColumn<>("Data");
            colData.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getDate()));

            TableColumn<Movimento, String> colCategorie = new TableColumn<>("Categoria");
            colCategorie.setCellValueFactory(cd ->
                    new SimpleStringProperty(
                            cd.getValue().getCategories().stream()
                                    .map(ICategory::getName)
                                    .findFirst()
                                    .orElse("")
                    )
            );

            table.getColumns().addAll(colImporto, colDescrizione, colData, colCategorie);


            TextField descrizioneField = new TextField();
            descrizioneField.setPromptText("Descrizione");

            TextField importoField = new TextField();
            importoField.setPromptText("Importo (es. 50.0)");

            ComboBox<ICategory> categoriaBox = new ComboBox<>();
            categoriaBox.getItems().setAll(new ArrayList<>(categoryManager.getAllCategories()));
            categoriaBox.setPromptText("Seleziona categoria");

            DatePicker dataPicker = new DatePicker(LocalDate.now());


            Button aggiungiBtn = new Button("Aggiungi Movimento");
            aggiungiBtn.setOnAction(e -> {
                try {
                    double importo = Double.parseDouble(importoField.getText().trim());
                    String descrizione = descrizioneField.getText().trim();
                    ICategory categoria = categoriaBox.getValue();
                    LocalDate data = dataPicker.getValue();

                    if (descrizione.isEmpty() || categoria == null || data == null) {
                        throw new IllegalArgumentException("Compila tutti i campi.");
                    }

                    Movimento nuovo = new Movimento(importo, data, descrizione);
                    nuovo.aggiungiCategoria(categoria);

                    movimentoManager.aggiungiMovimento(nuovo);
                    refresh();

                    descrizioneField.clear();
                    importoField.clear();
                    categoriaBox.setValue(null);
                    dataPicker.setValue(LocalDate.now());

                } catch (NumberFormatException nfe) {
                    new Alert(Alert.AlertType.ERROR, "Importo non valido.").showAndWait();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
                }
            });

            VBox layout = new VBox(10,
                    table,
                    descrizioneField,
                    importoField,
                    categoriaBox,
                    dataPicker,
                    aggiungiBtn
            );
            layout.setPadding(new Insets(10));

            return layout;
        }

        public void refresh() {
            table.getItems().setAll(
                    movimentoManager.getAllMovimenti().stream()
                            .filter(m -> m instanceof Movimento)
                            .map(m -> (Movimento) m)
                            .toList()
            );
        }
    }

