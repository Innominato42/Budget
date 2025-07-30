package it.unicam.cs.mpgc.jbudget109205.Model;

import java.util.ArrayList;
import java.util.List;

public class Category implements ICategory {
    private String name;
    private ICategory parent;
    private List<ICategory> children = new ArrayList<>();

    public Category(String name, ICategory parent) {
        this.name = name;
        this.parent = parent;
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }
    @Override
    public String getName() {
        return "";
    }

    @Override
    public ICategory getParent() {
        return null;
    }

    @Override
    public List<ICategory> getChildren() {
        return List.of();
    }
}
