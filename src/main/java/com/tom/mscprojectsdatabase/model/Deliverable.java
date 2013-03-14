package com.tom.mscprojectsdatabase.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Deliverable Entity
 * @author TOM
 */
@Entity
public class Deliverable implements Serializable
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

   @NotNull
   private String deadline;

   @ManyToOne
   private Project project;

    /**
     * Returns the deliverable ID
     * @return
     */
    public Long getId()
   {
      return this.id;
   }

    /**
     * Sets the deliverable ID
     * @param id
     */
    public void setId(final Long id)
   {
      this.id = id;
   }

  

     /**
     * Returns the name set for the deliverable
     * @return
     */
    public String getName()
   {
      return this.name;
   }

    /**
     * Sets the name of this deliverable
     * @param name
     */
    public void setName(final String name)
   {
      this.name = name;
   }

    /**
     * Gets the deadline
     * @return
     */
    public String getDeadline()
   {
      return this.deadline;
   }

    /**
     * Sets the deadline
     * @param deadline
     */
    public void setDeadline(final String deadline)
   {
      this.deadline = deadline;
   }

    /**
     * Gets the project in which this deliverable is set to 
     * @return
     */
    public Project getProject()
   {
      return this.project;
   }

    /**
     * Sets the project for this deliverable
     * @param project
     */
    public void setProject(final Project project)
   {
      this.project = project;
   }
}