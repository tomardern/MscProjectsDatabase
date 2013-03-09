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

import com.tom.mscprojectsdatabase.model.Organisation;

/**
 * Backing bean for Organisation entities.
 * <p>
 * This class provides CRUD functionality for all Organisation entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class OrganisationBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Organisation entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   private Organisation organisation;

   public Organisation getOrganisation()
   {
      return this.organisation;
   }

   @Inject
   private Conversation conversation;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
      }

      if (this.id == null)
      {
         this.organisation = this.example;
      }
      else
      {
         this.organisation = findById(getId());
      }
   }

   public Organisation findById(Long id)
   {

      return this.entityManager.find(Organisation.class, id);
   }

   /*
    * Support updating and deleting Organisation entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.organisation);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.organisation);
            return "view?faces-redirect=true&id=" + this.organisation.getId();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         this.entityManager.remove(findById(getId()));
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   /*
    * Support searching Organisation entities with pagination
    */

   private int page;
   private long count;
   private List<Organisation> pageItems;

   private Organisation example = new Organisation();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public Organisation getExample()
   {
      return this.example;
   }

   public void setExample(Organisation example)
   {
      this.example = example;
   }

   public void search()
   {
      this.page = 0;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<Organisation> root = countCriteria.from(Organisation.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Organisation> criteria = builder.createQuery(Organisation.class);
      root = criteria.from(Organisation.class);
      TypedQuery<Organisation> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Organisation> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String name = this.example.getName();
      if (name != null && !"".equals(name))
      {
         predicatesList.add(builder.like(root.<String> get("name"), '%' + name + '%'));
      }
      String username = this.example.getUsername();
      if (username != null && !"".equals(username))
      {
         predicatesList.add(builder.like(root.<String> get("username"), '%' + username + '%'));
      }
      String password = this.example.getPassword();
      if (password != null && !"".equals(password))
      {
         predicatesList.add(builder.like(root.<String> get("password"), '%' + password + '%'));
      }
      String email = this.example.getEmail();
      if (email != null && !"".equals(email))
      {
         predicatesList.add(builder.like(root.<String> get("email"), '%' + email + '%'));
      }
      String telephone = this.example.getTelephone();
      if (telephone != null && !"".equals(telephone))
      {
         predicatesList.add(builder.like(root.<String> get("telephone"), '%' + telephone + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Organisation> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Organisation entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Organisation> getAll()
   {

      CriteriaQuery<Organisation> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Organisation.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Organisation.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final OrganisationBean ejbProxy = this.sessionContext.getBusinessObject(OrganisationBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context,
               UIComponent component, String value)
         {

            return ejbProxy.findById(Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context,
               UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((Organisation) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Organisation add = new Organisation();

   public Organisation getAdd()
   {
      return this.add;
   }

   public Organisation getAdded()
   {
      Organisation added = this.add;
      this.add = new Organisation();
      return added;
   }
}