package com.tom.mscprojectsdatabase.controllers;

import com.tom.mscprojectsdatabase.model.Organisation;
import com.tom.mscprojectsdatabase.model.Project;
import com.tom.mscprojectsdatabase.model.Project_;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 *
 * @author TOM
 */
@Named
@Stateful
@ConversationScoped
public class ProjectBean implements Serializable {

    @EJB
    private OrganisationBean organisationBean;
    private static final long serialVersionUID = 1L;

    private Long orgid;
    private Long id;
    private int page;
    private long count;
    private List<Project> pageItems;
    private Project example = new Project();
    private Project project = new Project();

    @Inject
    private Conversation conversation;
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    
    /**
     * Returns the project ID
     * @return
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the project ID
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Gets this project for forms
     * @return
     */
    public Project getProject() {
        return this.project;
    }


    /**
     * Starts the conversation and redirects user to the create page
     * @return
     */
    public String create() {

        this.conversation.begin();
        return "create?faces-redirect=true";
    }

    /**
     * Sets the example when starting a create though the general walkthrough
     */
    public void startCreate() {
        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        this.project = this.example;
    }

    /**
     * On compeltion of the walkthough, redirect the user to the panel with 
     * Querystrings required
     * @return
     */
    public String finish() {
        return "/organisation/panel?faces-redirect=true&registration=complete&id=" + this.project.getOrganisation().getId();
    }

    /**
     * Retrieves the requested project, either by creating a new one or by using
     * the ID as set by the querystring
     */
    public void retrieve() {

        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        if (this.id == null) {
            this.project = this.example;
        } else {
            this.project = findById(getId());
        }
    }

    /**
     * Retrieve the list of deliverables for this project, then update as necessary
     */
    public void retrieveDelierable() {
        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        if (this.id == null) {
            this.project = this.example;
        } else {
            this.project = findById(getId());
        }

        update();

    }

    /**
     * Find a project by a given ID
     * @param id
     * @return
     */
    public Project findById(Long id) {

        return this.entityManager.find(Project.class, id);
    }


    /**
     * Update/Create the project and persist
     * @return
     */
    public String update() {
      
        try {

            //Any updates to the project are not approved.
            this.project.setApproved(false);

            if (this.id == null) {

                //Set the date
                Date date = new Date();
                this.project.setAdded(date);

                //If the organisation ID hasn't been set by a querystring
                if (this.getOrgid() != null) {
                    this.project.setOrganisation(organisationBean.findById(this.getOrgid()));
                }

                this.entityManager.persist(this.project);
                return "deliverables?faces-redirect=true&id=" + this.project.getId();
            } else {
                this.entityManager.merge(this.project);
                //If the organisation ID doesn't exist, we are in the walkthough
                if (this.getOrgid() == null) {
                    return "deliverables?faces-redirect=true&id=" + this.project.getId();
                } else {
                    return "/organisation/panel?faces-redirect=true&result=projupdated&id=" + this.getOrgid();
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    
    /**
     * Get the example project as used for search pages
     * @return
     */
    public Project getExample() {
        return this.example;
    }

    /**
     * Set the example project for search pages
     * @param example
     */
    public void setExample(Project example) {
        this.example = example;
    }

    /**
     * Carry out the search and reset the page
     */
    public void search() {
        this.page = 0;
    }

    /**
     * Paginate and return all the results for the resultSize requested
     * @param resultSize
     */
    public void paginate(int resultSize) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        // Populate this.count
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Project> root = countCriteria.from(Project.class);
        countCriteria = countCriteria.select(builder.count(root)).where(
                getSearchPredicates(root));
        this.count = this.entityManager.createQuery(countCriteria)
                .getSingleResult();

        // Populate this.pageItems
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);
        root = criteria.from(Project.class);
        TypedQuery<Project> query = this.entityManager.createQuery(
                criteria.select(root).where(getSearchPredicates(root)).orderBy(builder.desc(root.get(Project_.id))));

        query.setFirstResult(this.page * resultSize).setMaxResults(resultSize);

        this.pageItems = query.getResultList();
    }
    
    /**
     * Predicate the search function (Left from Forge Build)
     * @param resultSize
     */
    private Predicate[] getSearchPredicates(Root<Project> root) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        String name = this.example.getName();
        if (name != null && !"".equals(name)) {
            predicatesList.add(builder.like(root.<String>get("name"), '%' + name + '%'));
        }
        String question = this.example.getQuestion();
        if (question != null && !"".equals(question)) {
            predicatesList.add(builder.like(root.<String>get("question"), '%' + question + '%'));
        }
        String description = this.example.getDescription();
        if (description != null && !"".equals(description)) {
            predicatesList.add(builder.like(root.<String>get("description"), '%' + description + '%'));
        }
        String notes = this.example.getNotes();
        if (notes != null && !"".equals(notes)) {
            predicatesList.add(builder.like(root.<String>get("notes"), '%' + notes + '%'));
        }
        Organisation organisation = this.example.getOrganisation();

        if (organisation != null) {
            predicatesList.add(builder.equal(root.get("organisation"), organisation));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    /**
     * Return the list of projects
     * @return
     */
    public List<Project> getPageItems() {
        return this.pageItems;
    }

    /**
     * Return the total number of projects within the system.
     * @return
     */
    public long getCount() {
        return this.count;
    }


    /**
     * Get the organisation ID as set by the querystring
     * @return
     */
    public Long getOrgid() {
        return orgid;
    }


    /**
     * Set the organisation ID (Generally set via the querystring)
     * @param orgid
     */
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }
}