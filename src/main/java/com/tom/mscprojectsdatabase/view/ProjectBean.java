package com.tom.mscprojectsdatabase.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
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

import com.tom.mscprojectsdatabase.model.Project;
import com.tom.mscprojectsdatabase.model.Organisation;
import com.tom.mscprojectsdatabase.model.Project_;
import java.util.Date;
import javax.ejb.EJB;

/**
 * Backing bean for Project entities. <p> This class provides CRUD functionality
 * for all Project entities. It focuses purely on Java EE 6 standards (e.g.
 * <tt>&#64;ConversationScoped</tt> for state management,
 * <tt>PersistenceContext</tt> for persistence, <tt>CriteriaBuilder</tt> for
 * searches) rather than introducing a CRUD framework or custom base class.
 */
@Named
@Stateful
@ConversationScoped
public class ProjectBean implements Serializable {

    @EJB
    private OrganisationBean organisationBean;
    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving Project entities
     */
    private Long orgid;
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private Project project = new Project();

    public Project getProject() {
        return this.project;
    }
    @Inject
    private Conversation conversation;
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public String create() {

        this.conversation.begin();
        return "create?faces-redirect=true";
    }

    public void startCreate() {
        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        this.project = this.example;

    }

    public String finish() {

        return "/organisation/panel?faces-redirect=true&registration=complete&id=" + this.project.getOrganisation().getId();

    }

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

    public void retrieveDelierable() {

        /* Remove conversations to allow for proper deletion :) 
         * if (FacesContext.getCurrentInstance().isPostback())
         {
         return;
         } */

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

    public Project findById(Long id) {

        return this.entityManager.find(Project.class, id);
    }

    /*
     * Support updating and deleting Project entities
     */
    public String update() {
        //this.conversation.end();

        try {
            
           //Any updates to the project are not approved.
           this.project.setApproved(false);
                
            if (this.id == null) {
                            
                //Set the date
                Date date = new Date();
                this.project.setAdded(date);
                
                if (this.getOrgid() != null) {
                    this.project.setOrganisation(organisationBean.findById(this.getOrgid()));
                }

                this.entityManager.persist(this.project);
                return "deliverables?faces-redirect=true&id=" + this.project.getId();
            } else {
                this.entityManager.merge(this.project);
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

    public String delete() {
        this.conversation.end();

        try {
            this.entityManager.remove(findById(getId()));
            this.entityManager.flush();
            return "search?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    /*
     * Support searching Project entities with pagination
     */
    private int page;
    private long count;
    private List<Project> pageItems;
    private Project example = new Project();

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 1000; //TODO: Maybe make this set on a per page basis
    }

    public Project getExample() {
        return this.example;
    }

    public void setExample(Project example) {
        this.example = example;
    }

    public void search() {
        this.page = 0;
    }

    public void paginate() {

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

        query.setFirstResult(this.page * getPageSize()).setMaxResults(
                getPageSize());



        this.pageItems = query.getResultList();
    }

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

    public List<Project> getPageItems() {
        return this.pageItems;
    }

    public long getCount() {
        return this.count;
    }

    /*
     * Support listing and POSTing back Project entities (e.g. from inside an
     * HtmlSelectOneMenu)
     */
    public List<Project> getAll() {

        CriteriaQuery<Project> criteria = this.entityManager
                .getCriteriaBuilder().createQuery(Project.class);
        return this.entityManager.createQuery(
                criteria.select(criteria.from(Project.class))).getResultList();
    }
    @Resource
    private SessionContext sessionContext;

    public Converter getConverter() {

        final ProjectBean ejbProxy = this.sessionContext.getBusinessObject(ProjectBean.class);

        return new Converter() {
            @Override
            public Object getAsObject(FacesContext context,
                    UIComponent component, String value) {

                return ejbProxy.findById(Long.valueOf(value));
            }

            @Override
            public String getAsString(FacesContext context,
                    UIComponent component, Object value) {

                if (value == null) {
                    return "";
                }

                return String.valueOf(((Project) value).getId());
            }
        };
    }

    /*
     * Support adding children to bidirectional, one-to-many tables
     */
    private Project add = new Project();

    public Project getAdd() {
        return this.add;
    }

    public Project getAdded() {
        Project added = this.add;
        this.add = new Project();
        return added;
    }

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }
}