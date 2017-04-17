package com.example.getstarted.basicactions

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'book', action: 'index', method: 'GET')
        '/oauth2callback'(controller: 'oauth2Callback', action: 'index')
        "/books"(controller: 'book', action: 'index', method: 'GET')
        "/list"(controller: 'book', action: 'index', method: 'GET')
        "/read/$id"(controller: 'book', action: 'show', method: 'GET')
        '/create'(controller: 'book', action: 'save', method: 'POST')
        '/create'(controller: 'book', action: 'create', method: 'GET')
        "/delete/$id"(controller: 'book', action: 'delete', method: 'GET')
        "/update/$id"(controller: 'book', action: 'edit', method: 'GET')
        "/update/$id"(controller: 'book', action: 'update', method: 'POST')

        '/_ah/health'(controller: 'health') // App Engine flexible environment sends a request to
                                            // the path '/_ah/health' periodically to check if an
                                            // instance is still serving requests.
                                            //
                                            // For Google Compute Engine, we configure the load
                                            // balancer health checker to use this path.

        '/_ah/start'(controller: 'health')  // App Engine flexible environment sends a request to the
                                            // path '/_ah/start' when an instance starts up. This can
                                            // be useful for warming up resources like in-memory
                                            // caches and database connection pools.
                                            //
                                            // To acheive a similar result with Google Compute Engine,
                                            // a startup script
                                            //     https://g.co/cloud/compute/docs/startupscript
                                            // or an init() method on a servlet with loadOnStartup=1
                                            // can be used. See ListBookServlet for an example of
                                            // this.

        '/_ah/stop'(controller: 'health')  // App Engine flexible environment sends a request to the
                                           // path '/_ah/stop' when an instance is about to be shut
                                           // down.  This can be useful for cleaning up resources and
                                           // finishing data transactions.
                                           //
                                           // To acheive a similar result with Google Compute Engine,
                                           // a shutdown script
                                           // https://g.co/cloud/compute/docs/shutdownscript
                                           // can be used.

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
