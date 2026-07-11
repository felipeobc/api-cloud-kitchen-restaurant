package br.com.fiap.tech.challenge.cloud.kitchen.restaurant.apicloudkitchenrestaurant.domain.entity;

public class UserType {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private Boolean owner;

    // Construtor para criação de um novo UserType (ID será gerado pelo banco)
    public UserType(String name, String phone, String email, Boolean owner) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.owner = owner;
    }

    // Construtor para reconstruir um UserType a partir do banco (com ID existente)
    public UserType(Long id, String name, String phone, String email, Boolean owner) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.owner = owner;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }
}
