package com.kyleboon.contact.db

import com.kyleboon.contact.core.Contact
import com.yammer.dropwizard.hibernate.AbstractDAO
import com.yammer.metrics.annotation.Timed
import org.hibernate.SessionFactory
import org.hibernate.criterion.MatchMode
import org.hibernate.criterion.Restrictions

/**
 * User: kboon
 * Date: 11/19/12
 */
// TODO: write a unit test for this class
class ContactDAO extends AbstractDAO<Contact> {

    public ContactDAO(SessionFactory factory) {
        super(factory)
    }

    public Contact saveOrUpdate(Contact contact) {
        return persist(contact)
    }

    public Contact findById(Long id) {
        return currentSession()
                .createCriteria(Contact)
                .add(Restrictions.eq('id', id))
                .uniqueResult() as Contact
    }

    public List<Contact> list() {
        return currentSession()
                .createCriteria(Contact).list() as List<Contact>
    }

    public List<Contact> findByFirstName(String firstName) {
        return currentSession()
                .createCriteria(Contact)
                .add(Restrictions.ilike('firstName', firstName, MatchMode.ANYWHERE))
                .list() as List<Contact>
    }
}
