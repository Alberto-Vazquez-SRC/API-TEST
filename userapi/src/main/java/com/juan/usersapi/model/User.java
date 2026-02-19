package com.juan.usersapi.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;

public class User {

    private UUID id;
    private String email;
    private String name;
    private String phone;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String taxId;
    private LocalDateTime createdAt;
    private List<Address> addresses;

    public User() {}

    public User(UUID id, String email, String name, String phone,
                String password, String taxId,
                LocalDateTime createdAt, List<Address> addresses) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.taxId = taxId;
        this.createdAt = createdAt;
        this.addresses = addresses;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }
}
