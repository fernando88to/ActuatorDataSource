package actuatordatasource

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PeopleController {

    PeopleService peopleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond peopleService.list(params), model:[peopleCount: peopleService.count()]
    }

    def show(Long id) {
        respond peopleService.get(id)
    }

    def create() {
        respond new People(params)
    }

    def save(People people) {
        if (people == null) {
            notFound()
            return
        }

        try {
            peopleService.save(people)
        } catch (ValidationException e) {
            respond people.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'people.label', default: 'People'), people.id])
                redirect people
            }
            '*' { respond people, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond peopleService.get(id)
    }

    def update(People people) {
        if (people == null) {
            notFound()
            return
        }

        try {
            peopleService.save(people)
        } catch (ValidationException e) {
            respond people.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'people.label', default: 'People'), people.id])
                redirect people
            }
            '*'{ respond people, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        peopleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'people.label', default: 'People'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'people.label', default: 'People'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
