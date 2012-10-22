package com.futurenowsoftwaredesign.picturethis

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

/**
 * User must have authorisation to access application
 *
 */
@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class ImageController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.owner =  springSecurityService.currentUser
        [imageInstanceList: Image.list(params), imageInstanceTotal: Image.count()]
    }

    def create() {
        [imageInstance: new Image(params)]

    }
    /**
     * Modified to make sure the owner property is set automatically based on
     * the current user
     *
     */
    def save() {
        def imageInstance = new Image(params)
        if(imageInstance.owner == null) {
        imageInstance.setOwner(springSecurityService.currentUser)
        }
        if (!imageInstance.save(flush: true)) {
            render(view: "create", model: [imageInstance: imageInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'image.label', default: 'Image'), imageInstance.id])
        redirect(action: "show", id: imageInstance.id)
    }

    def show() {
        def imageInstance = Image.get(params.id)
        if (!imageInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), params.id])
            redirect(action: "list")
            return
        }

        [imageInstance: imageInstance]
    }

    def edit() {
        def imageInstance = Image.get(params.id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), params.id])
            redirect(action: "list")
            return
        }

        [imageInstance: imageInstance]
    }

    def update() {
        def imageInstance = Image.get(params.id)
        if (!imageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (imageInstance.version > version) {
                imageInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'image.label', default: 'Image')] as Object[],
                          "Another user has updated this Image while you were editing")
                render(view: "edit", model: [imageInstance: imageInstance])
                return
            }
        }

        imageInstance.properties = params

        if (!imageInstance.save(flush: true)) {
            render(view: "edit", model: [imageInstance: imageInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'image.label', default: 'Image'), imageInstance.id])
        redirect(action: "show", id: imageInstance.id)
    }

    def delete() {
        def imageInstance = Image.get(params.id)
        if (!imageInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), params.id])
            redirect(action: "list")
            return
        }

        try {
            imageInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'image.label', default: 'Image'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'image.label', default: 'Image'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    /**
     * Images are stored as a byte array rather than a specific file type
     * This is called be the view to render the bytes as an image
     */
    def renderImage = {
        def imageInstance = Image.get(params.id)
        response.setContentLength(imageInstance.picture.length)
        response.outputStream.write(imageInstance.picture)
    }
}
