package com.kyleboon.contact.core

import spock.lang.Specification

import static com.yammer.dropwizard.testing.JsonHelpers.asJson
import static com.yammer.dropwizard.testing.JsonHelpers.fromJson
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture

/**
 * User: kboon
 * Date: 2/3/13
 */
class ContactSpec extends Specification {
    Address address
    Contact contact


   def setup() {
       address = new Address(
               id:0,
               address1:"15 South 5th Street",
               address2:"",
               city:"Minneapolis",
               state:"MN",
               county:"Hennepin",
               zipCode:"55402"
       )

       contact = new Contact(
               id:0,
               firstName:"Kyle",
               lastName:"Boon",
               jobTitle:"developer",
               phoneNumber:"999-999-9999",
               address: address
       )
   }

    def 'Serializes to JSON as Expected'() {
        given:
        def expectedJSON = jsonFixture('fixtures/contact.json')

        when:
        def actualJSON = asJson(contact)

        then:
        assert expectedJSON == actualJSON
    }

    def 'Deserializes from JSON as Expected'() {
        when:
        def actualCommand = fromJson(jsonFixture('fixtures/contact.json'), Contact)

        then:
        assert actualCommand == contact
    }

}
