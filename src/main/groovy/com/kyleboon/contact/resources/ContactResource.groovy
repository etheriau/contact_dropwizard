package com.kyleboon.contact.resources

import com.kyleboon.contact.core.Contact
import com.kyleboon.contact.core.SearchCommand
import com.kyleboon.contact.db.ContactDAO

import javax.validation.Valid
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import com.yammer.dropwizard.hibernate.UnitOfWork
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed

import javax.xml.ws.WebServiceException
import javax.xml.ws.http.HTTPException

/**
 * User: kboon
 * Date: 11/19/12
 */
@Path('/contacts')
@Produces(MediaType.APPLICATION_JSON)
class ContactResource {
    private final ContactDAO contactDAO

    public ContactResource(ContactDAO contactDAO) {
        this.contactDAO = contactDAO
    }

    /**
     * Add or update a contact in the system.
     *
     * @param contact
     * <p>The contact to add to the system.</p>
     * <p> Example: {
     "firstName": "Kyle",
     "lastName": "Boon",
     "jobTitle": "developer",
     "phoneNumber": "999-999-9999",
     "address": {
     "id": 0,
     "address1":"15 South 5th Street",
     "address2":"",
     "city":"Minneapolis",
     "state":"MN",
     "county":"Hennepin",
     "zipCode":"55402"
     }
     }
     * </p>
     * @return
     */
    @Timed(name = 'createContact')
    @POST
    @UnitOfWork
    public Contact createContact(@Valid Contact contact) {
        return contactDAO.saveOrUpdate(deserializedContact)
    }

    /**
     * Get a known Contact by id
     *
     *  @param contactId
     * <p> The id of the contact
     * <p> Example: 100
     * @return the contact
     */
    @GET
    @Path('/{contact_id}')
    @Timed
    @UnitOfWork
    public Contact getContact(@PathParam(value = 'contact_id') long contactId) {
        Contact contact = contactDAO.findById(contactId)

        if (!contact) {
            throw new HTTPException(404)
        }

        return contact
    }

    /**
     * Returns a list of all contacts. Warning, this could be a BIG list!
     * @return all the contact
     */
    @Timed(name = 'listContact')
    @GET
    @UnitOfWork
    public List<Contact> listContact() {
        return contactDAO.list()
    }

    /**
     * Searches the contact list by first name and returns a list of matching contacts.
     * @param searchCommand
     * <p> a command the includes the first name to search for. </p>
     * <p> { "firstName" : "kyle" } </p>
     * @return
     */
    @Timed(name = 'searchForContact')
    @Path('/search')
    @GET
    @UnitOfWork
    public List<Contact> search(@Valid SearchCommand searchCommand) {
        return contactDAO.findByFirstName(searchCommand.firstName)
    }
}
