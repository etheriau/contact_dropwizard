package com.kyleboon.contact.db

import com.kyleboon.contact.core.Address
import com.kyleboon.contact.core.Contact
import org.joda.time.DateTime

/**
 * User: kboon
 * Date: 11/20/12
 */
class ContactDAOTest extends DAOTestHelper {
    ContactDAO contactDAO

    def setup() {
        contactDAO = new ContactDAO(sessionFactory)
    }

    @Override
    List<Class<?>> getEntities() {
        return [Contact, Address]
    }

    def 'persists and retrieves an enrollment file'() {
        given: 'an unpersisted contact with an address'
        Address address = new Address(
                address1:"15 South 5th Street",
                address2:"",
                city:"Minneapolis",
                state:"MN",
                county:"Hennepin",
                zipCode:"55402"
        )

        Contact contact = new Contact(
                firstName:"Kyle",
                lastName:"Boon",
                jobTitle:"developer",
                phoneNumber:"999-999-9999",
                address: address
        )

        when: 'it is persisted'
        Contact persistedContact = contactDAO.create(contact)
        Contact retreivedContact = contactDAO.findById(persistedContact.id)

        then: 'it is identical to the retrived version'
        assert persistedContact == retreivedContact
    }



}
