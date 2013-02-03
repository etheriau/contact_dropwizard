package com.kyleboon.contact.resources

import com.kyleboon.contact.core.Contact
import com.kyleboon.contact.db.ContactDAO

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import com.yammer.dropwizard.hibernate.UnitOfWork
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed

/**
 * User: kboon
 * Date: 11/19/12
 */
@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
class ContactResource {
    private final ContactDAO contactDAO

    public ContactResource(ContactDAO contactDAO) {
        this.contactDAO = contactDAO
    }

    @Timed(name = "create-contacts")
    @POST
    @UnitOfWork
    public Contact createContact(Contact deserializedContact) {
        return contactDAO.create(deserializedContact)
    }

    @Timed(name = "list-contacts")
    @GET
    @UnitOfWork
    public List<Contact> listContact() {
        return contactDAO.list()
    }
}
