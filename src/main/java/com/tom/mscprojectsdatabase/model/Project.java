package com.tom.mscprojectsdatabase.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Set;
import java.util.HashSet;
import com.tom.mscprojectsdatabase.model.Deliverable;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import com.tom.mscprojectsdatabase.model.Organisation;
import javax.persistence.ManyToOne;

@Entity
public class Project implements Serializable
{

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

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Project) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getQuestion()
   {
      return this.question;
   }

   public void setQuestion(final String question)
   {
      this.question = question;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public String getDeadline()
   {
      return this.deadline;
   }

   public void setDeadline(final String deadline)
   {
      this.deadline = deadline;
   }

   public Date getAdded()
   {
      return this.added;
   }

   public void setAdded(final Date added)
   {
      this.added = added;
   }

   public boolean getApproved()
   {
      return this.approved;
   }

   public void setApproved(final boolean approved)
   {
      this.approved = approved;
   }

   public String getNotes()
   {
      return this.notes;
   }

   public void setNotes(final String notes)
   {
      this.notes = notes;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      if (question != null && !question.trim().isEmpty())
         result += ", question: " + question;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      result += ", approved: " + approved;
      if (notes != null && !notes.trim().isEmpty())
         result += ", notes: " + notes;
      return result;
   }

   public Set<Deliverable> getDeliverables()
   {
      return this.deliverables;
   }

   public void setDeliverables(final Set<Deliverable> deliverables)
   {
      this.deliverables = deliverables;
   }

   public Organisation getOrganisation()
   {
      return this.organisation;
   }

   public void setOrganisation(final Organisation organisation)
   {
      this.organisation = organisation;
   }
}