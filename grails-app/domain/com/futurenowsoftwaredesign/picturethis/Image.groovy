package com.futurenowsoftwaredesign.picturethis

import javax.annotation.PostConstruct

class Image implements Comparable {

    def springSecurityService

    PicUser owner
    String caption
    String description
    byte[] picture

    static belongsTo = [PicUser]

    static optionals = ['description']
    static constraints = {
        picture(size: 0..6000000, blank:false)
        caption(size:  1..40, blank: false)
        description()

    }


    @PostConstruct
    public void init() {
        owner  = springSecurityService.getCurrentUser()
    }

    @Override
    int compareTo(Object o) {
        obj.id.compareTo(id)
    }
}
