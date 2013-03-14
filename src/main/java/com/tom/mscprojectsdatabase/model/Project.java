package com.tom.mscprojectsdatabase.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;
    @Version
    @Column(name = "version")
    private int version = 0;
    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private String question;
    @Column
    @NotNull
    private String description;
    @NotNull
    private String deadline;
    @Temporal(TemporalType.DATE)
    private Date added;
    @Column
    private boolean approved;
    @Column
    private String notes;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Deliverable> deliverables = new HashSet<Deliverable>();
    @ManyToOne
    private Organisation organisation;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public void setDeadline(final String deadline) {
        this.deadline = deadline;
    }

    public Date getAdded() {
        return this.added;
    }

    public void setAdded(final Date added) {
        this.added = added;
    }

    public boolean getApproved() {
        return this.approved;
    }

    public void setApproved(final boolean approved) {
        this.approved = approved;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public Set<Deliverable> getDeliverables() {
        return this.deliverables;
    }

    public void setDeliverables(final Set<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    public Organisation getOrganisation() {
        return this.organisation;
    }

    public void setOrganisation(final Organisation organisation) {
        this.organisation = organisation;
    }
}