package actuatordatasource

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PeopleServiceSpec extends Specification {

    PeopleService peopleService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new People(...).save(flush: true, failOnError: true)
        //new People(...).save(flush: true, failOnError: true)
        //People people = new People(...).save(flush: true, failOnError: true)
        //new People(...).save(flush: true, failOnError: true)
        //new People(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //people.id
    }

    void "test get"() {
        setupData()

        expect:
        peopleService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<People> peopleList = peopleService.list(max: 2, offset: 2)

        then:
        peopleList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        peopleService.count() == 5
    }

    void "test delete"() {
        Long peopleId = setupData()

        expect:
        peopleService.count() == 5

        when:
        peopleService.delete(peopleId)
        sessionFactory.currentSession.flush()

        then:
        peopleService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        People people = new People()
        peopleService.save(people)

        then:
        people.id != null
    }
}
