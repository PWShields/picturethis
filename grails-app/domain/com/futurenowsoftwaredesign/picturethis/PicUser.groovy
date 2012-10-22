package com.futurenowsoftwaredesign.picturethis

class PicUser {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    SortedSet images
    Integer imageCount = 0

    static hasMany = [ images : Image ]
	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

    String toString(){
        username
    }

	Set<Role> getAuthorities() {
		PicUserRole.findAllByPicUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}


}
