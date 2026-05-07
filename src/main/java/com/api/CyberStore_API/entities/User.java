package com.api.CyberStore_API.entities;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity // @Entity: Transforma esta classe em uma tabela no seu banco de dados
@Table(name = "tb_user") // @Table: Define o nome real da tabela no banco (evita erros com nomes reservados)
public class User implements Serializable {

    // Número de série para garantir a integridade dos dados durante a transferência/armazenamento
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    // Construtor vazio: O Hibernate/JPA exige isso para instanciar a classe internamente
    public User() {
    }

    // Construtor com argumentos: Facilita a criação do objeto
    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters e Setters: Métodos de acesso para garantir o encapsulamento (segurança do dado)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 4. hashCode e equals (baseado apenas no ID para comparação de objetos)
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
