# grails-embedded-nullables

An example application using grails 3.3.1 and gorm 6.1.8 to demonstrate an issue 
about domain entities having embedded components.

The issue is that the default nullable constraint is ignored for embedded components.

The default constraint in application.groovy

    grails.gorm.default.constraints = {
        '*'(nullable: true)
    } 
    
The embedded component: Address.groovy

    class Address {
        String street
        String zipCode
        String city
    } 
    
The domain entity: User.groovy

    class User {
        String username
        String password
        String comment
        Address address = new Address()
    
        static constraints = {
        }
        static embedded = ['address']
    }
       
The demo code showing the behaviour: BootStrap.groovy

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
    
The expected behaviour is, that the application starts because the default constraints should 
allow null for all fields.

But the validation fails because of the folling errors:

    Property [street] of class [class grails.embedded.nullables.Address] cannot be null
    Property [zipCode] of class [class grails.embedded.nullables.Address] cannot be null
    