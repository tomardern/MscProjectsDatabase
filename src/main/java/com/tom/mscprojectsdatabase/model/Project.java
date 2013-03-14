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

/**
 * Project Entity
 * @author TOM
 */
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

    /**
     * Gets the ID
     * @return
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the ID
     * @param id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the project
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the project
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the question 
     * @return
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Sets the question
     * @param question
     */
    public void setQuestion(final String question) {
        this.question = question;
    }

    /**
     * Gets the description
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * gets the deadline
     * @return
     */
    public String getDeadline() {
        return this.deadline;
    }

    /**
     * Sets the deadline
     * @param deadline
     */
    public void setDeadline(final String deadline) {
        this.deadline = deadline;
    }

    /**
     * Gets the date added
     * @return
     */
    public Date getAdded() {
        return this.added;
    }

    /**
     * Sets the date added
     * @param added
     */
    public void setAdded(final Date added) {
        this.added = added;
    }

    /**
     * Gets the approved state (True = Approved, False = unapproved)
     * @return
     */
    public boolean getApproved() {
        return this.approved;
    }

    /**
     * Sets the approved state  (True = Approved, False = unapproved)
     * @param approved
     */
    public void setApproved(final boolean approved) {
        this.approved = approved;
    }

    /**
     * Gets the notes for this project. 
     * @return
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Sets the notes for this project
     * @param notes
     */
    public void setNotes(final String notes) {
        this.notes = notes;
    }

    /**
     * Returns the list of deliverables (name and dates required)
     * @return
     */
    public Set<Deliverable> getDeliverables() {
        return this.deliverables;
    }

    /**
     * Sets the deliverable list
     * @param deliverables
     */
    public void setDeliverables(final Set<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    /**
     * Gets the organisation in which this project relates too
     * @return
     */
    public Organisation getOrganisation() {
        return this.organisation;
    }

    /**
     * Sets the organisation in which this project relates too
     * @param organisation
     */
    public void setOrganisation(final Organisation organisation) {
        this.organisation = organisation;
    }
}