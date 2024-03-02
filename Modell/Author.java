package iit.unimiskolc.FRI.model;

import jakarta.persistence.*;
import lombok.experimental.FieldNameConstants;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Author")
@FieldNameConstants
public class Author {
    @Id
    private String author_id;
    private String givenName;
    private String familyName;
    private String fullName;
    private String status;
    private String auxName;
    private Integer publicationCount;
    private Integer citationCount;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "AuthorPublication",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "publication_id")}
    )
    private final Set<Publication> publications = new HashSet<>();

    public Author() {

    }

    public Author(final String author_id, final String givenName, final String familyName, final String fullName, final String status, final String auxName, final Integer publicationCount, final Integer citationCount) {
        this.author_id = author_id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.fullName = fullName;
        this.status = status;
        this.auxName = auxName;
        this.publicationCount = publicationCount;
        this.citationCount = citationCount;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String idauthor) {
        this.author_id = author_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String name) {
        this.fullName = name;
    }

    public String getGiveName() {
        return givenName;
    }

    public void setGiveName(final String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(final String familyName) {
        this.familyName = familyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getAuxName() {
        return auxName;
    }

    public void setAuxName(final String auxName) {
        this.auxName = auxName;
    }

    public Integer getPublicationCount() {
        return publicationCount;
    }

    public void setPublicationCount(final Integer publicationCount) {
        this.publicationCount = publicationCount;
    }

    public Integer getCitationCount() {
        return citationCount;
    }

    public void setCitationCount(final Integer citationCount) {
        this.citationCount = citationCount;
    }

    public Set<Publication> getPublications() {
        return publications;
    }
}
