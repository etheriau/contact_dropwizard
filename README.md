# Introduction

The drop wizard example application was developed to, as its name implies, provide examples of some of the features
present in drop wizard.

# Overview


# Running The Application

To test the example application run the following commands.

* To package the example run.

        gradle shade

* To setup the h2 database run.

        gradle migrate

* To run the server run.

        gradle run

* To test the server after it has been started run.

        curl  -H "Content-Type: application/json" -H "Accept: application/json"  -X POST -d @src/test/resources/fixtures/contact.json http://localhost:8080/contacts
        curl http://localhost:8080/contacts

# Testing with Gradle

Currently only the unit tests are exposed to maven and intellij must be used
to run the integration tests. To run the tests execute:

        gradle test