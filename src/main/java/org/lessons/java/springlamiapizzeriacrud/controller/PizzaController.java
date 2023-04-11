package org.lessons.java.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.java.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.repository.IngredientRepository;
import org.lessons.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepo;

    @Autowired
    private IngredientRepository ingredientRepo;

    //INDEX
    @GetMapping
    public String index(@RequestParam(name = "s") Optional<String> s, @RequestParam(name = "p") Optional<BigDecimal> p, Model model){
        List<Pizza> pizzas;

        if(s.isEmpty()){
            pizzas =  pizzaRepo.findAll();
            if(!p.isEmpty()){
                pizzas = pizzaRepo.findByPriceLessThanEqual(p.get());
            }
        } else if (!p.isEmpty()) {
            pizzas = pizzaRepo.findByNameContainingIgnoreCaseAndPriceLessThanEqual(s.get(), p.get());
        } else {
            pizzas = pizzaRepo.findByNameContainingIgnoreCase(s.get());
            model.addAttribute("search", s.get());
        }

        model.addAttribute("pizzas", pizzas);

        return "/pizzas/index";
    }

    //ADVANCED SEARCH
    @GetMapping("/advanced-search")
    public String advancedSearch(){
        return "/pizzas/advanced-search";
    }

    //SHOW
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Pizza pizza = pizzaRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("pizza", pizza);
        return "/pizzas/show";
    }

    //CREATE
    @GetMapping("/create")
    public String create(Model model){
        List<Ingredient> ingredients = ingredientRepo.findAll();
        model.addAttribute("pizza" , new Pizza());
        model.addAttribute("ingredients", ingredients);
        return "/pizzas/create";
    }

    //STORE
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            List<Ingredient> ingredients = ingredientRepo.findAll();
            model.addAttribute("ingredients", ingredients);
            return "/pizzas/create";
        }
        redirectAttributes.addFlashAttribute("success", "La pizza " + formPizza.getName() + " è stata creata");
        pizzaRepo.save(formPizza);
        return "redirect:/pizzas";
    }

    //EDIT
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Pizza pizza = pizzaRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Ingredient> ingredients = ingredientRepo.findAll();
        model.addAttribute("pizza", pizza);
        model.addAttribute("ingredients", ingredients);
        return "/pizzas/edit";
    }

    //UPDATE
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Pizza pizza, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            List<Ingredient> ingredients = ingredientRepo.findAll();
            model.addAttribute("ingredients", ingredients);
            return "/pizzas/edit";
        }
        redirectAttributes.addFlashAttribute("success", "La pizza " + pizza.getName() + " è stata aggiornata");
        pizzaRepo.save(pizza);
        return "redirect:/pizzas";
    }

    //DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Pizza pizza = pizzaRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        redirectAttributes.addFlashAttribute("danger", "La pizza " + pizza.getName() + " è stata cancellata");
        pizzaRepo.deleteById(id);
        return "redirect:/pizzas";
    }

}
