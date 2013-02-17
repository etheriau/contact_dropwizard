package com.kyleboon.contact

import com.fasterxml.jackson.annotation.JsonProperty
import com.yammer.dropwizard.config.Configuration
import com.yammer.dropwizard.db.DatabaseConfiguration

import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * User: kboon
 * Date: 11/14/12
 */
class ContactsConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty
    DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration()

    @JsonProperty
    String kylesString
}
