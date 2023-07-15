package com.social.media.confessionmedia.resourceserver.model;

import com.social.media.confessionmedia.authorizationserver.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotNull
    @Size(min = 5, max = 1000)
    private String postName;

    private String url;

    @NotNull
    @Size(min = 10, max = 10000)
    private String description;

    private Integer voteCount = 0;
    private Instant createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

}
