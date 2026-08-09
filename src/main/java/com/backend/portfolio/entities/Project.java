package com.backend.portfolio.entities;

import java.time.LocalDateTime;
import java.util.List;


import com.backend.portfolio.entities.enums.State;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Proyectos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @OneToMany(
        mappedBy = "project",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ProjectImage> images;
    private String urlGitHub;
    private String website;
    private LocalDateTime date_create;
    private LocalDateTime date_update;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "project_technology",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private List<Technology> technologies;

    @Enumerated(EnumType.STRING)
    private State state;

    @PrePersist
    protected void onCreate() {
        date_create = LocalDateTime.now();

        if (state == null) {
            state = State.PENDING;

        }
    }

    @PreUpdate
    protected void onUpdate() {
        date_update = LocalDateTime.now();
    }

}
