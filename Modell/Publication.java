package iit.unimiskolc.FRI.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(
        name = "Publication",
        indexes = @Index(columnList = Publication.Fields.mtid)
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mtid;

    private String type;

    @Column(columnDefinition = "LONGTEXT")
    private String title;
    private String status;

    private Integer publishedYear;

    private String firstPage;
    private String lastPage;
    private String volume;
    @Column(columnDefinition = "LONGTEXT")
    private String label;
    private String issue;
    private Integer pageLength;

    @Column(columnDefinition = "LONGTEXT")
    private String journalLabel;

    private Integer authorCount;

    @ManyToMany(mappedBy = Author.Fields.publications)
    private final Set<Author> authors = new HashSet<>();

    @OneToMany(
            mappedBy = PublicationLink.Fields.publication,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<PublicationLink> links;

}