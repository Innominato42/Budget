package it.unicam.cs.mpgc.jbudget109205.Model;

import java.util.List;

public interface ICategory {
    String getName();
    ICategory getParent();
    List<ICategory> getChildren();
}
