package com.example.MarketPlace.security;

import com.example.MarketPlace.model.dao.UsuarioRepository;
import com.example.MarketPlace.model.entity.Usuario;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class UsuarioDetailsConfig implements UserDetailsService{
    
    @Autowired
    UsuarioRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = repository.usuario(login);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }else{
            return new User(usuario.getLogin(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
        }
    }   
}
