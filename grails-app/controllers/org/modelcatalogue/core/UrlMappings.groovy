package org.modelcatalogue.core

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "dataModelMappingSuggestions")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
