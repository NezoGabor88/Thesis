package iit.unimiskolc.FRI.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;


@Entity
@Table(name = "publication_link")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
public class PublicationLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Publication publication;

    @Column(columnDefinition = "LONGTEXT")
    private String link;

    private String sourceName;
}