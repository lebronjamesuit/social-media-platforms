package com.general.business.resourceserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 10, max = 10000)
    private String description;

    @OneToMany(fetch = LAZY)
    private List<Post> posts;

    private Instant createdDate;

}
