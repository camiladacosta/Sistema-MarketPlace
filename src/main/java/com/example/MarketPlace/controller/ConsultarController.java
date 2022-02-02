package com.example.MarketPlace.controller;

import com.example.MarketPlace.model.dao.VendasRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Controller
@RequestMapping("consultar")
public class ConsultarController {

    @Autowired
    VendasRepository repository;

    @ResponseBody
    @GetMapping("/exemplo")
    public String exemplo() {
        return "Controller de Consulta!";
    }  
    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("vendas", repository.vendas());
        return new ModelAndView("/consultar/list", model);
    }
    @GetMapping("/list/{id}")
    public ModelAndView edit(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("vendas", repository.venda(id));
        return new ModelAndView("/consultar/list", model);
    }
}
