package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface IMovimento {
    LocalDate getDate();

    double getAmount();

    String getDescription();

    Set<ICategory> getCategories();

    UUID getId();
}
