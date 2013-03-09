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
import com.tom.mscprojectsdatabase.model.Project;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
public class Organisation implements Serializable
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
   private String username;

   @Column
   @NotNull
   private String password;

   @Column
   @NotNull
   private String email;

   @Column
   @NotNull
   private String telephone;

   @Column
   private String fax;

   @Column
   @NotNull
   private String orgname;

   @Column
   private String drescription;

   @Column
   private String address;

   @Column
   private String website;

   @Column
   private boolean verified;

   @Temporal(TemporalType.DATE)
   private Date added;

   @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
   private Set<Project> projects = new HashSet<Project>();

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
         return id.equals(((Organisation) that).id);
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

   public String getUsername()
   {
      return this.username;
   }

   public void setUsername(final String username)
   {
      this.username = username;
   }

   public String getPassword()
   {
      return this.password;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getTelephone()
   {
      return this.telephone;
   }

   public void setTelephone(final String telephone)
   {
      this.telephone = telephone;
   }

   public String getFax()
   {
      return this.fax;
   }

   public void setFax(final String fax)
   {
      this.fax = fax;
   }

   public String getOrgname()
   {
      return this.orgname;
   }

   public void setOrgname(final String orgname)
   {
      this.orgname = orgname;
   }

   public String getDrescription()
   {
      return this.drescription;
   }

   public void setDrescription(final String drescription)
   {
      this.drescription = drescription;
   }

   public String getAddress()
   {
      return this.address;
   }

   public void setAddress(final String address)
   {
      this.address = address;
   }

   public String getWebsite()
   {
      return this.website;
   }

   public void setWebsite(final String website)
   {
      this.website = website;
   }

   public boolean getVerified()
   {
      return this.verified;
   }

   public void setVerified(final boolean verified)
   {
      this.verified = verified;
   }

   public Date getAdded()
   {
      return this.added;
   }

   public void setAdded(final Date added)
   {
      this.added = added;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      if (username != null && !username.trim().isEmpty())
         result += ", username: " + username;
      if (password != null && !password.trim().isEmpty())
         result += ", password: " + password;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (telephone != null && !telephone.trim().isEmpty())
         result += ", telephone: " + telephone;
      if (fax != null && !fax.trim().isEmpty())
         result += ", fax: " + fax;
      if (orgname != null && !orgname.trim().isEmpty())
         result += ", orgname: " + orgname;
      if (drescription != null && !drescription.trim().isEmpty())
         result += ", drescription: " + drescription;
      if (address != null && !address.trim().isEmpty())
         result += ", address: " + address;
      if (website != null && !website.trim().isEmpty())
         result += ", website: " + website;
      result += ", verified: " + verified;
      return result;
   }

   public Set<Project> getProjects()
   {
      return this.projects;
   }

   public void setProjects(final Set<Project> projects)
   {
      this.projects = projects;
   }
}