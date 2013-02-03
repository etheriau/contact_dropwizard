package com.kyleboon.contact.resources

import org.junit.Test
import com.kyleboon.contact.core.Contact
import com.kyleboon.contact.db.ContactDAO
import groovy.mock.interceptor.MockFor
import org.hibernate.SessionFactory

/**
 * User: kboon
 * Date: 11/19/12
 */
class ContactResourceUnitTests {
    @Test
    public void createContact_callsHibernateDAO() {
        Contact expectedContact = new Contact()

        MockFor contactDAOMock = new MockFor(ContactDAO)
        MockFor sessionFactory = new MockFor(SessionFactory)

        contactDAOMock.demand.create() { Contact contact ->
            assert contact == expectedContact
            return contact
        }

        ContactDAO mockDAOInstance = contactDAOMock.proxyInstance(sessionFactory.proxyInstance())

        ContactResource contactResource = new ContactResource(mockDAOInstance)

        Contact newContact  = contactResource.createContact(expectedContact)

        assert newContact
        contactDAOMock.verify(contactResource.contactDAO)

    }

    @Test
    public void list_callsHibernateDAO() {
        List<Contact> expectedContactList = [new Contact(), new Contact(), new Contact()]

        MockFor contactDAOMock = new MockFor(ContactDAO)
        MockFor sessionFactory = new MockFor(SessionFactory)

        contactDAOMock.demand.list() {  ->
            return expectedContactList
        }

        ContactDAO mockDAOInstance = contactDAOMock.proxyInstance(sessionFactory.proxyInstance())

        ContactResource contactResource = new ContactResource(mockDAOInstance)

        List<Contact> contactList = contactResource.listContact()

        contactDAOMock.verify(contactResource.contactDAO)
        assert contactList == expectedContactList
    }
}
