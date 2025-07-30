package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.LocalDate;
import java.util.Set;

public class Movimento implements IMovimento{
    private LocalDate date;
    private  double amount;
    private  String description;
    private  Set<ICategory> categories;

    public Movimento(LocalDate date, double amount, String description, Set<ICategory> categories) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.categories = categories;
    }

    @Override
    public LocalDate getDate() { return date; }

    @Override
    public double getAmount() { return amount; }

    @Override
    public String getDescription() { return description; }

    @Override
    public Set<ICategory> getCategories() { return categories; }

}
