package org.lessons.java.springlamiapizzeriacrud.controller;

import org.lessons.java.springlamiapizzeriacrud.model.Pizza;
import org.lessons.java.springlamiapizzeriacrud.model.SpecialOffer;
import org.lessons.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.lessons.java.springlamiapizzeriacrud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/special-offer")
public class SpecialOfferController {
    @Autowired
    private SpecialOfferRepository specialOfferRepo;
    @Autowired
    private PizzaRepository pizzaRepo;

    //CREATE
    @GetMapping("/create")
    public String create(@RequestParam(name="id") Optional<Integer> id, Model model){
        SpecialOffer specialOffer = new SpecialOffer();
        if(id.isPresent()){
            Pizza pizza = pizzaRepo.findById(id.get()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
            specialOffer.setPizza(pizza);
        }
        model.addAttribute(specialOffer);
        return "/specialOffer/create";
    }

    //STORE
    @PostMapping("/create")
    public String store(@ModelAttribute SpecialOffer specialOffer) {
        specialOfferRepo.save(specialOffer);
        return "redirect:/pizzas/" + Integer.toString(specialOffer.getPizza().getId());
    }

    //EDIT
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        SpecialOffer specialOffer = specialOfferRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute(specialOffer);
        return "/specialOffer/edit";
    }

    //UPDATE
    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute SpecialOffer specialOffer){
        specialOfferRepo.save(specialOffer);
        return "redirect:/pizzas/" + specialOffer.getPizza().getId();
    }
}
