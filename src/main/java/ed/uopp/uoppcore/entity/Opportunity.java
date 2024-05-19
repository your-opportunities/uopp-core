package ed.uopp.uoppcore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

//    TODO: review EAGER
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "opportunity_category",
            joinColumns = @JoinColumn(name = "opportunity_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "opportunity_format",
            joinColumns = @JoinColumn(name = "opportunity_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "format_id", referencedColumnName = "id")
    )
    private Set<Format> formats = new HashSet<>();

    @Column(nullable = false)
    private Boolean isAsap;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private LocalDateTime postCreatedDatetime;

    @Column(nullable = false)
    private LocalDateTime postScrapperDatetime;

    @Enumerated(EnumType.STRING)
    private OpportunityStatus opportunityStatus;

    @Column
    private LocalDateTime createdDatetime;

    @PrePersist
    protected void onCreate() {
        createdDatetime = LocalDateTime.now();
    }
}
