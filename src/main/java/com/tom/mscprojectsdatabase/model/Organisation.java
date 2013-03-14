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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

//http://stackoverflow.com/questions/3404853/multiple-unique-constraints-in-jpa
/**
 * Organisation Entity
 * @author TOM
 */
@Entity
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = "username"))
public class Organisation implements Serializable {

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
    @Column(unique = true)
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

    /**
     *
     * @return
     */
    public Long getId() {
        return this.id;
    }

    /**
     *
     * @param id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getVersion() {
        return this.version;
    }

    /**
     *
     * @param version
     */
    public void setVersion(final int version) {
        this.version = version;
    }

    /**
     *
     * @param that
     * @return
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        if (id != null) {
            return id.equals(((Organisation) that).id);
        }
        return super.equals(that);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return this.username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     *
     * @param telephone
     */
    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    /**
     *
     * @return
     */
    public String getFax() {
        return this.fax;
    }

    /**
     *
     * @param fax
     */
    public void setFax(final String fax) {
        this.fax = fax;
    }

    /**
     *
     * @return
     */
    public String getOrgname() {
        return this.orgname;
    }

    /**
     *
     * @param orgname
     */
    public void setOrgname(final String orgname) {
        this.orgname = orgname;
    }

    /**
     *
     * @return
     */
    public String getDrescription() {
        return this.drescription;
    }

    /**
     *
     * @param drescription
     */
    public void setDrescription(final String drescription) {
        this.drescription = drescription;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return this.address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String getWebsite() {
        return this.website;
    }

    /**
     *
     * @param website
     */
    public void setWebsite(final String website) {
        this.website = website;
    }

    /**
     *
     * @return
     */
    public boolean getVerified() {
        return this.verified;
    }

    /**
     *
     * @param verified
     */
    public void setVerified(final boolean verified) {
        this.verified = verified;
    }

    /**
     *
     * @return
     */
    public Date getAdded() {
        return this.added;
    }

    /**
     *
     * @param added
     */
    public void setAdded(final Date added) {
        this.added = added;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return orgname;
    }

    /**
     *
     * @return
     */
    public Set<Project> getProjects() {
        return this.projects;
    }

    /**
     *
     * @param projects
     */
    public void setProjects(final Set<Project> projects) {
        this.projects = projects;
    }
}