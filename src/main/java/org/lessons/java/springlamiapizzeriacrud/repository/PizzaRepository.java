package org.lessons.java.springlamiapizzeriacrud.repository;

import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    public List<Pizza> findByNameContainingIgnoreCase(String name);
    public List<Pizza> findByNameContainingIgnoreCaseAndPriceLessThanEqual(String name, BigDecimal price);
    public List<Pizza> findByPriceLessThanEqual(BigDecimal price);
}
