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
import java.util.Set;

public class ScadenzarioView {
    private final Scadenzario scadenzario;
    private final CategoryManager categoryManager;
    private final TableView<MovimentoProgrammato> table;

    public ScadenzarioView(Scadenzario scadenzario, CategoryManager categoryManager) {
        this.scadenzario = scadenzario;
        this.categoryManager = categoryManager;
        this.table = new TableView<>();
    }

    public VBox getView() {

        TableColumn<MovimentoProgrammato, Double> colImporto = new TableColumn<>("Importo");
        colImporto.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().getImporto()).asObject());

        TableColumn<MovimentoProgrammato, String> colDescrizione = new TableColumn<>("Descrizione");
        colDescrizione.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getDescrizione()));

        TableColumn<MovimentoProgrammato, LocalDate> colData = new TableColumn<>("Data esecuzione");
        colData.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getDataEsecuzione()));

        TableColumn<MovimentoProgrammato, String> colCategorie = new TableColumn<>("Categoria");
        colCategorie.setCellValueFactory(cd ->
                new SimpleStringProperty(
                        cd.getValue().getCategorie().stream()
                                .map(ICategory::getName)
                                .findFirst()
                                .orElse("")
                )
        );

        table.getColumns().addAll(colImporto, colDescrizione, colData, colCategorie);

        // --- Form input ---
        TextField descrizioneField = new TextField();
        descrizioneField.setPromptText("Descrizione");

        TextField importoField = new TextField();
        importoField.setPromptText("Importo (es. 50.0)");

        ComboBox<ICategory> categoriaBox = new ComboBox<>();
        categoriaBox.getItems().setAll(new ArrayList<>(categoryManager.getAllCategories()));
        categoriaBox.setPromptText("Seleziona categoria");

        DatePicker dataPicker = new DatePicker(LocalDate.now());


        Button aggiungiBtn = new Button("Aggiungi Programmato");
        aggiungiBtn.setOnAction(e -> {
            try {
                double importo = Double.parseDouble(importoField.getText().trim());
                String descrizione = descrizioneField.getText().trim();
                ICategory categoria = categoriaBox.getValue();
                LocalDate data = dataPicker.getValue();

                if (descrizione.isEmpty() || categoria == null || data == null) {
                    throw new IllegalArgumentException("Compila tutti i campi.");
                }

                MovimentoProgrammato nuovo = new MovimentoProgrammato(
                        data,
                        importo,
                        descrizione,
                        Set.of(categoria)
                );

                scadenzario.aggiungiMovimento(nuovo);
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


        Button eseguiBtn = new Button("Esegui scadenze di oggi");
        eseguiBtn.setOnAction(e -> {
            scadenzario.eseguiMovimenti(LocalDate.now());
            refresh();
        });

        VBox layout = new VBox(10,
                table,
                descrizioneField,
                importoField,
                categoriaBox,
                dataPicker,
                new VBox(5, aggiungiBtn, eseguiBtn)
        );
        layout.setPadding(new Insets(10));

        return layout;
    }

    public void refresh() {
        table.getItems().setAll(scadenzario.getMovimenti());
    }
}

