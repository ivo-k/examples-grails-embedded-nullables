package grails.embedded.nullables

class User {

    String username

    String password

    String comment

    Address address = new Address()

    static constraints = {
    }

    static embedded = ['address']
}
