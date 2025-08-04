package it.unicam.cs.mpgc.jbudget109205.Model;

import java.time.YearMonth;

public class Budget {

    private final ICategory category;
    private final YearMonth month;
    private final double limit;

    public Budget(ICategory category, YearMonth month, double limit) {
        this.category = category;
        this.month = month;
        this.limit = limit;
    }

    public ICategory getCategory() {
        return category;
    }

    public YearMonth getMonth() {
        return month;
    }

    public double getLimit() {
        return limit;
    }
}
