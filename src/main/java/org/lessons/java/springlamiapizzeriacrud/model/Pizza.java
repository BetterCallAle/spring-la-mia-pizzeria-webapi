package org.lessons.java.springlamiapizzeriacrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Il campo nome non può essere vuoto")
    @Size(min = 1, max = 255, message = "Il nome non può avere più di 255 caratteri")
    @Column(nullable = false)
    private String name;
    @Size(min=10, max=255, message="La descrizione deve avere minimo 10 caratteri e massimo 255")
    private String description;
    @DecimalMin(value="0.01",message = "Il prezzo non può essere zero o inferiore a zero")
    @NotNull(message = "Il prezzo deve essere inserito")
    @Column(nullable = false)
    private BigDecimal price;

    @JsonIgnore
    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL)
    private List<SpecialOffer> specialOffers;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "ingredient_pizza",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients;

    //GETTERS
    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
