package com.example.MarketPlace.controller;

import com.example.MarketPlace.model.dao.ClienteRepository;
import com.example.MarketPlace.model.dao.RoleRepository;
import com.example.MarketPlace.model.dao.VendasRepository;
import com.example.MarketPlace.model.entity.ClientePF;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Controller
@RequestMapping("clientes")
public class ClientePFController {
    @Autowired
    ClienteRepository repositoryCliente;
    
    @Autowired
    VendasRepository repository;
    
    @Autowired
    RoleRepository repositoryRole;
    
    @ResponseBody
    @GetMapping("/exemplo")
    public String exemplo() {
        return "Controller de Cliente!";
    }
    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("clientes", repositoryCliente.clientes());
        return new ModelAndView("/clientes/list", model);
    }
    @GetMapping("/form")
    public ModelAndView form(ClientePF clientePF) {
        return new ModelAndView ("/clientes/form");
    }
    @PostMapping("/save")
    public ModelAndView save(@Valid ClientePF clientePF, BindingResult result) {
        if (result.hasErrors()) {
            return form(clientePF);//para manter o objeto com dados preenchidos
        }
        repositoryCliente.save(clientePF);
        return new ModelAndView("redirect:/clientes/list");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") int id) {
        repositoryCliente.remove(id);
        return new ModelAndView("redirect:/clientes/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("clientePF", repositoryCliente.clientePF(id));
        return new ModelAndView("/clientes/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid ClientePF clientePF, BindingResult result) {
        if (result.hasErrors()) {
            return form(clientePF);//para manter o objeto com dados preenchidos
        }
        /*Usuario usuario = new Usuario();
        usuario.setLogin(clientePF.getUsuario().getLogin());
        usuario.setPassword(new BCryptPasswordEncoder().encode(clientePF.getUsuario().getPassword()));
        usuario.getRoles().add(repositoryRole.role(2));
        clientePF.setUsuario(usuario);//FUNCIONOU AQUI COM O CASCADE TYPE ALL*/
        repositoryCliente.update(clientePF);
        return new ModelAndView("redirect:/clientes/list");
    }    
    
    @PostMapping("/listarCliente")
    public ModelAndView listarCliente(@RequestParam(value = "buscacliente") String buscacliente, ModelMap model){
        model.addAttribute("clientes", repositoryCliente.clientes(buscacliente));
        return new ModelAndView("/clientes/list", model);//filtra vendas pelo nome do cliente
    }
    
}

