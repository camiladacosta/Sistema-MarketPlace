package com.example.MarketPlace.controller;

import com.example.MarketPlace.model.dao.ClienteRepository;
import com.example.MarketPlace.model.dao.RoleRepository;
import com.example.MarketPlace.model.dao.UsuarioRepository;
import com.example.MarketPlace.model.entity.ClientePF;
import com.example.MarketPlace.model.entity.Usuario;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository repositoryUsuario;
    
    @Autowired
    ClienteRepository repositoryCliente;
    
    @Autowired
    RoleRepository repositoryRole;

    @ResponseBody
    @GetMapping("/exemplo")
    public String exemplo() {
        return "Controller de Usuarios!";
    }

    @GetMapping("/form")
    public ModelAndView form(ClientePF clientePF) {
        return new ModelAndView ("/usuario/cadastro");//o html
    }//carregando usuario form/cadastro.html
    
    @PostMapping("/save")
    public ModelAndView save(@Valid ClientePF clientePF, BindingResult result) {
        if (result.hasErrors()) {
            return form(clientePF);//para manter o objeto com dados preenchidos
        }
        repositoryCliente.save(clientePF);
        return new ModelAndView("redirect:/login");
    }   
    @PostMapping("/update")
    public ModelAndView update(@Valid ClientePF clientePF, BindingResult result) {
        if (result.hasErrors()) {
            return form(clientePF);//para manter o objeto com dados preenchidos
        }
        Usuario usuario = new Usuario();
        usuario.setLogin(clientePF.getUsuario().getLogin());
        usuario.setPassword(new BCryptPasswordEncoder().encode(clientePF.getUsuario().getPassword()));
        usuario.getRoles().add(repositoryRole.role(2));
        clientePF.setUsuario(usuario);//FUNCIONOU AQUI COM O CASCADE TYPE ALL
        repositoryCliente.update(clientePF);
        return new ModelAndView("redirect:/login");
    }    
}