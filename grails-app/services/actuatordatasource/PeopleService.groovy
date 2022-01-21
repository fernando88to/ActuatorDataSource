package actuatordatasource

import grails.gorm.services.Service

@Service(People)
interface PeopleService {

    People get(Serializable id)

    List<People> list(Map args)

    Long count()

    void delete(Serializable id)

    People save(People people)

}