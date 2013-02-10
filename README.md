# Introduction



# Overview


# Running The Application

To test the example application run the following commands.

* To run the tests run

`gradle test`

* To package the example run.

        gradle shade

* To setup the h2 database run.

        gradle migrate

* To run the server run.

        gradle run

* To test the server after it has been started run.

        curl  -H "Content-Type: application/json" -H "Accept: application/json"  -X POST -d @src/test/resources/fixtures/contact.json http://localhost:8080/contacts
        curl http://localhost:8080/contacts

