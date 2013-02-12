package com.kyleboon.contact.db

import com.kyleboon.contact.core.Address
import com.kyleboon.contact.core.Contact
import org.joda.time.DateTime

/**
 * User: kboon
 * Date: 11/20/12
 */
class ContactDAOSpec extends DAOTestHelper {
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
        Contact persistedContact = contactDAO.saveOrUpdate(contact)
        Contact retreivedContact = contactDAO.findById(persistedContact.id)

        then: 'it is identical to the retrived version'
        assert persistedContact == retreivedContact
    }

    def 'finds a list of contacts'() {
        given: '2 contacts'
        Contact contact = new Contact(
                firstName:"Kyle",
                lastName:"Boon",
                jobTitle:"developer",
                phoneNumber:"999-999-9999",
                address: null
        )

        Contact contact2 = new Contact(
                firstName:"Kyle",
                lastName:"Boon",
                jobTitle:"developer",
                phoneNumber:"999-999-9999",
                address: null
        )

        contactDAO.saveOrUpdate(contact)
        contactDAO.saveOrUpdate(contact2)

        when: 'we get the list of all contact'
        List<Contact> contactList = contactDAO.list()

        then: 'the list has 2 contacts in it'
        assert contactList.size() == 2
    }

    def 'finds contact by first name'() {
        given: 'a contact with the first name of Kyle'
        Contact contact = new Contact(
                firstName:"Kyle",
                lastName:"Boon",
                jobTitle:"developer",
                phoneNumber:"999-999-9999",
                address: null
        )

        contactDAO.saveOrUpdate(contact)

        when: 'we search for Kyle'
        List<Contact> contactList = contactDAO.findByFirstName('Kyle')

        then: 'we find him'
        assert contactList.size() == 1
    }



}
