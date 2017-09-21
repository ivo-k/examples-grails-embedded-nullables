package grails.embedded.nullables

class BootStrap {
    def messageSource

    def init = { servletContext ->
        User u = new User()

        u.username = 'admin'
        u.address.city = 'Berlin'

        boolean valid = u.validate()

        if (!valid) {
            u.errors.fieldErrors.each {
                println(messageSource.getMessage(it, Locale.US))
            }
        }

        assert valid

        User.withTransaction {
            u.save(flush: true)
        }
    }

    def destroy = {
    }
}
