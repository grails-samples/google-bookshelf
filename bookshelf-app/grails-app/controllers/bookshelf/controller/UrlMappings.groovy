package bookshelf.controller

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }
        // tag::homeMap[]
        '/'(controller: 'book', action: 'index', method: 'GET')
        // end::homeMap[]

        '/oauth2callback'(controller: 'oauth2Callback', action: 'index')
        '/books'(controller: 'book', action: 'index', method: 'GET')
        '/list'(controller: 'book', action: 'index', method: 'GET')

        // tag::showMap[]
        "/read/$id"(controller: 'book', action: 'show', method: 'GET')
        // end::showMap[]

        // tag::saveMap[]
        '/create'(controller: 'book', action: 'save', method: 'POST')
        // end::saveMap[]

        // tag::createMap[]
        '/create'(controller: 'book', action: 'create', method: 'GET')
        // end::createMap[]

        // tag::deleteMap[]
        "/delete/$id"(controller: 'book', action: 'delete', method: 'GET')
        // end::deleteMap[]

        // tag::editMap[]
        "/update/$id"(controller: 'book', action: 'edit', method: 'GET')
        // end::editMap[]
        // tag::updateMap[]
        "/update/$id"(controller: 'book', action: 'update', method: 'POST')
        // end::updateMap[]

        // tag::ahHealth[]
        '/_ah/health'(controller: 'health')
        // end::ahHealth[]
        // tag::ahStart[]
        '/_ah/start'(controller: 'health')
        // end::ahStart[]
        // tag::ahStop[]
        '/_ah/stop'(controller: 'health')
        // end::ahStop[]

        '500'(view: '/error')
        '404'(view: '/notFound')
    }
}
