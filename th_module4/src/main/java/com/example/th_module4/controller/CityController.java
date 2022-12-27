package com.example.th_module4.controller ;

import com.example.th_module4.model.City;
import com.example.th_module4.model.Country;
import com.example.th_module4.service.city.ICityService;
import com.example.th_module4.service.country.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class CityController {
    @Autowired
    private ICityService cityService;

    @Autowired
    private ICountryService countryService;

    @GetMapping
    public ModelAndView showListCities() {
        ModelAndView modelAndView = new ModelAndView();
        List<City> cities = (List<City>) cityService.findAll();
        modelAndView.addObject("cities", cities);
        modelAndView.setViewName("/list");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        List<Country> countries = (List<Country>) countryService.findAll();
        modelAndView.addObject("countries", countries);
        City city = new City();
        modelAndView.addObject("city", city);
        modelAndView.setViewName("/create");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showFormEditCity(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/edit");
        List<Country> countries = (List<Country>) countryService.findAll();
        modelAndView.addObject("countries", countries);
        Optional<City> city = cityService.findById(id);
        if (!city.isPresent()) {
            modelAndView.addObject("error", "the city doesn't exist");
            return modelAndView;
        }
        City newCity = city.get();
        modelAndView.addObject("city", newCity);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showInfoCity(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/Information");
        Optional<City> city = cityService.findById(id);
        if (!city.isPresent()) {
            modelAndView.addObject("error", "the city doesn't exist");
            return modelAndView;
        }
        City newCity = city.get();
        modelAndView.addObject("city", newCity);
        return modelAndView;
    }


    @GetMapping("/deleted/{id}")
    public ModelAndView delede(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        cityService.remove(id);
        List<City> cities = (List<City>) cityService.findAll();
        modelAndView.addObject("cities", cities);
        modelAndView.setViewName("/list");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createNewCity(@Validated @ModelAttribute City city, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        List<City> cities = (List<City>) cityService.findAll();
        modelAndView.addObject("cities", cities);
        if (result.hasFieldErrors()) {
            List<String> errors = new ArrayList<>();
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError o : list) {
                errors.add(o.getDefaultMessage());
            }
            List<Country> countries = (List<Country>) countryService.findAll();
            modelAndView.addObject("countries", countries);
            modelAndView.addObject("city", city);
            modelAndView.addObject("error", errors);
            modelAndView.setViewName("/create");
            return modelAndView;
        }
        cityService.save(city);
        modelAndView.setViewName("/list");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editCity(@PathVariable Long id, @Validated @ModelAttribute City city, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        List<City> cities = (List<City>) cityService.findAll();
        modelAndView.addObject("cities", cities);
        if (result.hasFieldErrors()) {
            List<String> errors = new ArrayList<>();
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError o : list) {
                errors.add(o.getDefaultMessage());
            }
            modelAndView.addObject("city", city);
            modelAndView.addObject("error", errors);
            modelAndView.setViewName("/edit");
            return modelAndView;
        }
        modelAndView.setViewName("/list");
        cityService.save(city);
        return modelAndView;
    }


    @PostMapping("/deleted/{id}")
    public ModelAndView deletedCity(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        cityService.remove(id);
        List<City> cities = (List<City>) cityService.findAll();
        modelAndView.addObject("cities", cities);
        modelAndView.setViewName("/list");
        return modelAndView;
    }
}
