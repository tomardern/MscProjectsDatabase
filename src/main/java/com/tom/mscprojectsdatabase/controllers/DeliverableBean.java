package com.tom.mscprojectsdatabase.controllers;

import com.tom.mscprojectsdatabase.model.Deliverable;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;


/**
 * DeliverableBean Class
 * @author TOM
 */
@Named
@Stateful
@ConversationScoped
public class DeliverableBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Deliverable add = new Deliverable();

    /**
     * Get the add deliverable. Used as a placeholder when creating new deliverables
     * @return
     */
    public Deliverable getAdd() {
        return this.add;
    }

    /**
     * Get the most recently added deliverable
     * @return
     */
    public Deliverable getAdded() {
        Deliverable added = this.add;
        this.add = new Deliverable();
        return added;
    }
}