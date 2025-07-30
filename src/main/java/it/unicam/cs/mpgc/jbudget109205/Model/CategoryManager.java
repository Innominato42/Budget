package it.unicam.cs.mpgc.jbudget109205.Model;

import java.util.*;

public class CategoryManager {

    private final ICategory rootCategory;
    private final Map<String, ICategory> categoriesByName;

    public CategoryManager() {
        this.rootCategory = new Category("Tutte le Categorie", null);
        this.categoriesByName = new HashMap<>();
        categoriesByName.put(rootCategory.getName(), rootCategory);
    }

    /**
     * Aggiunge una nuova categoria sotto una categoria esistente.
     *
     * @param name       il nome della nuova categoria
     * @param parentName il nome della categoria genitore
     * @return la nuova categoria creata
     * @throws IllegalArgumentException se il genitore non esiste o la categoria esiste già
     */
    public ICategory addCategory(String name, String parentName) {
        if (categoriesByName.containsKey(name)) {
            throw new IllegalArgumentException("Categoria già esistente: " + name);
        }

        ICategory parent = categoriesByName.get(parentName);
        if (parent == null) {
            throw new IllegalArgumentException("Categoria padre non trovata: " + parentName);
        }

        ICategory newCategory = new Category(name, parent);
        categoriesByName.put(name, newCategory);
        return newCategory;
    }

    /**
     * Restituisce la categoria associata al nome, se esiste.
     *
     * @param name nome della categoria
     * @return categoria trovata o null
     */
    public ICategory getCategory(String name) {
        return categoriesByName.get(name);
    }

    /**
     * Restituisce tutte le categorie registrate.
     *
     * @return insieme delle categorie
     */
    public Collection<ICategory> getAllCategories() {
        return categoriesByName.values();
    }

    /**
     * Restituisce la categoria radice ("Tutte le Categorie").
     *
     * @return la categoria root
     */
    public ICategory getRootCategory() {
        return rootCategory;
    }

    /**
     * Verifica se una categoria esiste.
     *
     * @param name nome della categoria
     * @return true se esiste, false altrimenti
     */
    public boolean exists(String name) {
        return categoriesByName.containsKey(name);
    }

    /**
     * Restituisce tutte le sottocategorie (ricorsivamente) a partire da una categoria.
     *
     * @param category categoria radice
     * @return lista piatta di tutte le sottocategorie
     */
    public List<ICategory> getAllSubcategories(ICategory category) {
        List<ICategory> result = new ArrayList<>();
        for (ICategory child : category.getChildren()) {
            result.add(child);
            result.addAll(getAllSubcategories(child));
        }
        return result;
    }
}

