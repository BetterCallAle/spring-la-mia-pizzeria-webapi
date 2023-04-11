package org.lessons.java.springlamiapizzeriacrud.api;

import jakarta.validation.Valid;
import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v.1/pizzas")
public class PizzaRestController {
    @Autowired
    private PizzaRepository pizzaRepo;

    //INDEX
    @GetMapping
    public List<Pizza> index(@RequestParam(name = "name") Optional<String> name){
        List<Pizza> pizzas;
        if(name.isPresent()){
            pizzas = pizzaRepo.findByNameContainingIgnoreCase(name.get());
        } else {
            pizzas = pizzaRepo.findAll();
        }

        return pizzas;
    }

    //SHOW
    @GetMapping("/{id}")
    public Pizza show(@PathVariable Integer id){
        return pizzaRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //CREATE
    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return pizzaRepo.save(pizza);
    }

    //UPDATE
    @PutMapping("/{id}")
    public Pizza update(@Valid @PathVariable Integer id, @RequestBody Pizza pizza, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Pizza pizzaById = pizzaRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return pizzaRepo.save(pizza);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        pizzaRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        pizzaRepo.deleteById(id);
    }
}
