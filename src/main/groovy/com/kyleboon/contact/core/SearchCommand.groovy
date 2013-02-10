package com.kyleboon.contact.core

import org.hibernate.validator.constraints.NotEmpty

import javax.validation.constraints.NotNull


class SearchCommand {
    @NotNull
    @NotEmpty
    String firstName


}
