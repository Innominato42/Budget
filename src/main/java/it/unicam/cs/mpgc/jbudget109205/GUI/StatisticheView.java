package it.unicam.cs.mpgc.jbudget109205.GUI;

import it.unicam.cs.mpgc.jbudget109205.Model.ICategory;
import it.unicam.cs.mpgc.jbudget109205.Model.IStatistiche;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public class StatisticheView {

    private final IStatistiche statistiche;

    private final DatePicker monthPicker;
    private final TableView<RigaCategoria> table;
    private final PieChart pieChart;
    private final Label lblTopCategory;
    private final Label lblMediaMensile;

    public StatisticheView(IStatistiche statistiche) {
        this.statistiche = statistiche;

        monthPicker = new DatePicker(LocalDate.now().withDayOfMonth(1));
        table = new TableView<>();
        pieChart = new PieChart();
        lblTopCategory = new Label();
        lblMediaMensile = new Label();
    }

    public VBox getView() {

        Label lblMese = new Label("Mese:");
        Button btnAggiorna = new Button("Aggiorna");
        btnAggiorna.setOnAction(e -> refresh());

        HBox topBar = new HBox(10, lblMese, monthPicker, btnAggiorna);
        topBar.setPadding(new Insets(10));


        TableColumn<RigaCategoria, String> colCat = new TableColumn<>("Categoria");
        colCat.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().categoria));

        TableColumn<RigaCategoria, Double> colSpeso = new TableColumn<>("Speso");
        colSpeso.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().speso).asObject());

        TableColumn<RigaCategoria, Double> colPerc = new TableColumn<>("%");
        colPerc.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().percentuale).asObject());

        table.getColumns().addAll(colCat, colSpeso, colPerc);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);


        VBox infoBox = new VBox(6,
                new Label("Categoria piu costosa:"), lblTopCategory,
                new Label("Media mensile (tutti i mesi):"), lblMediaMensile
        );
        infoBox.setPadding(new Insets(10));


        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(false);


        SplitPane split = new SplitPane();
        split.getItems().addAll(table, pieChart);
        split.setDividerPositions(0.55);

        VBox root = new VBox(10, topBar, split, infoBox);
        root.setPadding(new Insets(10));


        refresh();

        return root;
    }

    /** Aggiorna tabella, grafico e indicatori in base al mese selezionato. */
    public void refresh() {
        YearMonth mese = YearMonth.from(monthPicker.getValue() != null
                ? monthPicker.getValue()
                : LocalDate.now().withDayOfMonth(1));


        Map<ICategory, Double> spese = statistiche.getSpesePerCategoria(mese);
        Map<ICategory, Double> percentuali = statistiche.getPercentualiPerCategoria(mese);


        table.getItems().clear();
        spese.forEach((cat, speso) -> {
            double perc = percentuali.getOrDefault(cat, 0.0);
            table.getItems().add(new RigaCategoria(cat.getName(), speso, perc));
        });


        pieChart.getData().clear();
        percentuali.forEach((cat, perc) -> {
            if (perc != 0.0) {
                pieChart.getData().add(new PieChart.Data(cat.getName(), Math.abs(perc)));
            }
        });


        ICategory top = statistiche.getCategoriaPiuCostosa(mese);
        lblTopCategory.setText(top != null ? top.getName() : "—");


        double media = statistiche.getMediaMensile();
        lblMediaMensile.setText(String.format("%.2f", media));
    }


    private static class RigaCategoria {
        final String categoria;
        final double speso;
        final double percentuale;
        RigaCategoria(String categoria, double speso, double percentuale) {
            this.categoria = categoria;
            this.speso = speso;
            this.percentuale = percentuale;
        }
    }
}