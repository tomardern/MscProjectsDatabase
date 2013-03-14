package com.tom.mscprojectsdatabase.controllers;

import com.tom.mscprojectsdatabase.model.Organisation;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.criteria.CriteriaQuery;

@Named
@Stateful
@ConversationScoped
public class OrganisationBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Organisation userOrganisation;
    private Long id;
    
    @Inject
    private Conversation conversation;
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    
    private Organisation example = new Organisation();
    
    @Resource
    private SessionContext sessionContext;
       
    
    
    
    

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private Organisation organisation;

    public Organisation getOrganisation() {
        return this.organisation;
    }


    public String create() {

        this.conversation.begin();
        return "create?faces-redirect=true";
    }

    public void retrieve() {

        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        if (this.id == null) {
            this.organisation = this.example;
        } else {
            this.organisation = findById(getId());
        }
    }

    public String startLogin() {
        Organisation orgID = this.getUserOrganisation();
        return "panel?faces-redirect=true&id=" + orgID.getId();
    }

    public Organisation findById(Long id) {
        return this.entityManager.find(Organisation.class, id);
    }

    /*
     * Support updating and deleting Organisation entities
     */
    public String update() {
        this.conversation.end();

        try {
            if (this.id == null) {
                Date date = new Date();
                this.organisation.setAdded(date);
                this.entityManager.persist(this.organisation);
                return "created";
            } else {
                this.entityManager.merge(this.organisation);
                return "panel?faces-redirect=true&id=" + this.organisation.getId() + "&result=updated";
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String newProject() {
        return "/project/create";
    }


    /*
     * Support listing and POSTing back Organisation entities (e.g. from inside an
     * HtmlSelectOneMenu)
     */
    public List<Organisation> getAll() {

        CriteriaQuery<Organisation> criteria = this.entityManager
                .getCriteriaBuilder().createQuery(Organisation.class);
        return this.entityManager.createQuery(
                criteria.select(criteria.from(Organisation.class))).getResultList();
    }


     public Converter getConverter() {

        final OrganisationBean ejbProxy = this.sessionContext.getBusinessObject(OrganisationBean.class);

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

                return String.valueOf(((Organisation) value).getId());
            }
        };
    } 

    /**
     * @return the userOrganisation
     */
    public Organisation getUserOrganisation() {
        return userOrganisation;
    }

    /**
     * @param userOrganisation the userOrganisation to set
     */
    public void setUserOrganisation(Organisation userOrganisation) {
        this.userOrganisation = userOrganisation;
    }
}