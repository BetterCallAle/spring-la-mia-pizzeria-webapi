package org.lessons.java.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.java.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.java.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepo;

    //INDEX
    @GetMapping
    public String index(Model model, @RequestParam(name = "id") Optional<Integer> id){
        List<Ingredient> ingredients = ingredientRepo.findAll();
        model.addAttribute("ingredients", ingredients);

        if(id.isEmpty()){
            model.addAttribute("singleIngredient", new Ingredient());
        } else {
            Ingredient ingredient = ingredientRepo.findById(id.get()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
            model.addAttribute("ingredientUpdate", ingredient);
        }

        return "/ingredients/index";
    }

    //STORE
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute Ingredient ingredient, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/ingredients/index";
        }
        ingredientRepo.save(ingredient);
        return "redirect:/ingredients";
    }

    //UPDATE
    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Ingredient ingredient){
        ingredientRepo.save(ingredient);
        return "redirect:/ingredients";
    }

    //DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Ingredient ingredient = ingredientRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        ingredientRepo.delete(ingredient);
        return "redirect:/ingredients";
    }

}
