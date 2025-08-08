package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Movimento implements IMovimento{
    private final UUID id;
    private LocalDate date;
    private  double amount;
    private  String description;
    private  Set<ICategory> categories;

    public Movimento(UUID id,LocalDate date, double amount, String description, Set<ICategory> categories) {
        this.id=id;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.categories = categories;
    }
    public Movimento(double amount, LocalDate date, String description) {
        this(UUID.randomUUID(), date, amount, description, new HashSet<>());
    }

    @Override
    public LocalDate getDate() { return date; }

    @Override
    public double getAmount() { return amount; }

    @Override
    public String getDescription() { return description; }

    @Override
    public Set<ICategory> getCategories() { return categories; }

    @Override
    public UUID getId() {
        return id;
    }
    public void aggiungiCategoria(ICategory category) {
        categories.add(category);
    }

}
