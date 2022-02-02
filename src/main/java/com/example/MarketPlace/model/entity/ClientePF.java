package com.example.MarketPlace.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_clientePF")
public class ClientePF extends Cliente{
    @NotBlank(message = "Nome não pode estar em branco!")
    private String nome;
    
    @NotNull
    @Size(min = 11, max = 11, message ="CPF precisa ter 11 digitos!")
    private String cpf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
