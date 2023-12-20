package com.travelBill.api.core.family;

import com.travelBill.api.core.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "family")
public class Family {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "budget_resolver_id")
    private User budgetResolver;

    @ManyToMany
    @JoinTable(name = "family_members",
            joinColumns = @JoinColumn(name = "family_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> familyMembers;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBudgetResolver() {
        return budgetResolver;
    }

    public void setBudgetResolver(User budgetResolver) {
        this.budgetResolver = budgetResolver;
    }

    public List<User> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<User> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
