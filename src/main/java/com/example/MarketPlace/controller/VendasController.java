package com.example.MarketPlace.controller;

import com.example.MarketPlace.model.dao.ClienteRepository;
import com.example.MarketPlace.model.dao.ProdutoRepository;
import com.example.MarketPlace.model.dao.VendasRepository;
import com.example.MarketPlace.model.entity.ClientePF;
import com.example.MarketPlace.model.entity.ItemVenda;
import com.example.MarketPlace.model.entity.Produto;
import com.example.MarketPlace.model.entity.Venda;
import java.time.LocalDate;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Scope("request")
@Transactional
@Controller
@RequestMapping("vendas")
public class VendasController {
    
    @Autowired
    VendasRepository repository;
    
    @Autowired
    ProdutoRepository repositoryP;
    
    @Autowired
    ClienteRepository repositoryC;
    
    @Autowired
    Venda venda;
    
    @ResponseBody
    @GetMapping("/exemplo")
    public String exemplo() {
        return "Controller de Vendas!";
    }
    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("vendas", repository.vendas()); //listar produtos
        return new ModelAndView("/vendas/list", model);
    }
    @GetMapping("/form")
    public ModelAndView form(ItemVenda item, ModelMap model) {
        model.addAttribute("produtos", repositoryP.produtos());
        model.addAttribute("clientes", repositoryC.clientes());
        return new ModelAndView("/vendas/formVendas", model);
    }
    
    @PostMapping("/add")
    public ModelAndView add(@Valid ItemVenda item, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return form(item, model);//para manter o objeto com dados preenchidos
        }
        Produto produto = repositoryP.produto(item.getProduto().getId());
        item.setProduto(produto);   
        item.setVenda(venda);
        venda.getItens().add(item);
        return new ModelAndView("redirect:/vendas/form");
    }
    
    @PostMapping("/save")
    public ModelAndView save(ClientePF c, RedirectAttributes attributes ) {//precisa ClientePF
        if(venda.getItens().isEmpty()){
            attributes.addFlashAttribute("erro", "Não possui itens no carrinho");
        } 
        if(c.getId() == 0){
            attributes.addFlashAttribute("errocliente", "Selecione um cliente");  
        }
        if(!attributes.getFlashAttributes().isEmpty()){
            return new ModelAndView("redirect:/vendas/form");
        }
        ClientePF cliente = repositoryC.clientePF(c.getId());
        venda.setCliente(cliente);
        this.venda.setId(0);
        repository.save(venda);  
        venda.getItens().clear();
        return new ModelAndView("redirect:/vendas/list");//o metodo do controller
    }
    @GetMapping("/remove/{posicao}")
    public ModelAndView remove(@PathVariable("posicao") int posicao) {
        venda.getItens().remove(posicao);
        return new ModelAndView("redirect:/vendas/form");
    }
    
    @PostMapping("/filtrarData")
    public ModelAndView filtrarData(@RequestParam(value = "databusca") String databusca, ModelMap model){
        model.addAttribute("vendas", repository.vendas(LocalDate.parse(databusca)));
        return new ModelAndView("/vendas/list", model);
    }
}