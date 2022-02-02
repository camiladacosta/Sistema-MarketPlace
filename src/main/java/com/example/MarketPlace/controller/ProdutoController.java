package com.example.MarketPlace.controller;

import com.example.MarketPlace.model.dao.ProdutoRepository;
import com.example.MarketPlace.model.entity.Produto;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Controller
@RequestMapping("produtos")
public class ProdutoController {
  
    @Autowired
    ProdutoRepository repository;
    
    @ResponseBody
    @GetMapping("/exemplo")
    public String exemplo() {
        return "Controller de Produtos!";
    }
    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("produtos", repository.produtos());
        return new ModelAndView("/produtos/list", model);
    }
    @GetMapping("/form")
    public ModelAndView form(Produto produto) {//alterei para modelandview em todos
        return new ModelAndView ("/produtos/formCadastro"); //a pagina html
    }
    /*@PostMapping("/save")  ORIGINAL
    public ModelAndView save(Produto produto) {
        repository.save(produto);
        return new ModelAndView("redirect:/produtos/list");//o metodo do controller
    }*/
    
    @PostMapping("/save")
    public ModelAndView save(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return form(produto);//para manter o objeto com dados preenchidos
        }
        repository.save(produto);
        return new ModelAndView("redirect:/produtos/list");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") int id) {
        repository.remove(id);
        return new ModelAndView("redirect:/produtos/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("produto", repository.produto(id));
        return new ModelAndView("/produtos/formCadastro", model); //pagina html
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return form(produto);//para manter o objeto com dados preenchidos
        }
        repository.update(produto);
        return new ModelAndView("redirect:/produtos/list");
    }
}
