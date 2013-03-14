package com.tom.mscprojectsdatabase.controllers;

import com.tom.mscprojectsdatabase.model.Deliverable;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;


@Named
@Stateful
@ConversationScoped
public class DeliverableBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Deliverable add = new Deliverable();

    public Deliverable getAdd() {
        return this.add;
    }

    public Deliverable getAdded() {
        Deliverable added = this.add;
        this.add = new Deliverable();
        return added;
    }
}