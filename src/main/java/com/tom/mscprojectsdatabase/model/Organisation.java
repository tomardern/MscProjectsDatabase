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
     * Gets the ID of the organisation
     * @return ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the ID of the organisation
     * @param id
     */
    public void setId(final Long id) {
        this.id = id;
    }

 
    /**
     * The name of the organisation
     * @return Name of user 
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the organisation
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the username
     * @return Username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets the password
     * @return Password String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets the email
     * @return Email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the user's email address
     * @param email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the users telephone number
     * @return Telephone as string
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * Sets the users telephone number
     * @param telephone
     */
    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    /**
     * Gets their fax details
     * @return Fax (as strng)
     */
    public String getFax() {
        return this.fax;
    }

    /**
     * Sets their fax details
     * @param fax
     */
    public void setFax(final String fax) {
        this.fax = fax;
    }

    /**
     * Gets the organisation's name
     * @return Organisation's name
     */
    public String getOrgname() {
        return this.orgname;
    }

    /**
     * Sets the organisation's Name
     * @param orgname
     */
    public void setOrgname(final String orgname) {
        this.orgname = orgname;
    }

    /**
     * Gets the description
     * @return Description
     */
    public String getDrescription() {
        return this.drescription;
    }

    /**
     * Sets the description
     * @param drescription
     */
    public void setDrescription(final String drescription) {
        this.drescription = drescription;
    }

    /**
     * Gets the address
     * @return Address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets the address
     * @param address
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Gets the website
     * @return
     */
    public String getWebsite() {
        return this.website;
    }

    /**
     * Sets the website
     * @param website
     */
    public void setWebsite(final String website) {
        this.website = website;
    }

    /**
     * Gets the verification status
     * @return
     */
    public boolean getVerified() {
        return this.verified;
    }

    /**
     * Sets the verification status
     * @param verified
     */
    public void setVerified(final boolean verified) {
        this.verified = verified;
    }

    /**
     * Gets the added date
     * @return
     */
    public Date getAdded() {
        return this.added;
    }

    /**
     * Sets the added date
     * @param added
     */
    public void setAdded(final Date added) {
        this.added = added;
    }

    /**
     * ToString method
     * @return the organiation's name
     */
    @Override
    public String toString() {
        return orgname;
    }

    /**
     * Get the associated projects
     * @return
     */
    public Set<Project> getProjects() {
        return this.projects;
    }

    /**
     * Sets the associated project
     * @param projects
     */
    public void setProjects(final Set<Project> projects) {
        this.projects = projects;
    }
}