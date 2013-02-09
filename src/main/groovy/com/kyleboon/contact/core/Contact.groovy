package com.kyleboon.contact.core

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

/**
 * User: kboon
 * Date: 11/19/12
 */
@Entity
@Table(name = 'contacts')
@ToString
@EqualsAndHashCode
class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    Long id

    @Column(name = 'first_name', nullable = false)
    @JsonProperty
    String firstName

    @Column(name = 'last_name', nullable = false)
    @JsonProperty
    String lastName

    @Column(name = 'job_title', nullable = false)
    @JsonProperty
    String jobTitle

    @Column(name = 'phone_number', nullable = false)
    @JsonProperty
    String phoneNumber


    @JoinColumn(name = 'address_id')
    @JsonProperty
    @OneToOne(cascade = CascadeType.ALL)
    Address address
}
