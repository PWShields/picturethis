package com.futurenowsoftwaredesign.picturethis

import org.apache.commons.lang.builder.HashCodeBuilder

class PicUserRole implements Serializable {

	PicUser picUser
	Role role

	boolean equals(other) {
		if (!(other instanceof PicUserRole)) {
			return false
		}

		other.picUser?.id == picUser?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (picUser) builder.append(picUser.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static PicUserRole get(long picUserId, long roleId) {
		find 'from PicUserRole where picUser.id=:picUserId and role.id=:roleId',
			[picUserId: picUserId, roleId: roleId]
	}

	static PicUserRole create(PicUser picUser, Role role, boolean flush = false) {
		new PicUserRole(picUser: picUser, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(PicUser picUser, Role role, boolean flush = false) {
		PicUserRole instance = PicUserRole.findByPicUserAndRole(picUser, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(PicUser picUser) {
		executeUpdate 'DELETE FROM PicUserRole WHERE picUser=:picUser', [picUser: picUser]
	}

	static void removeAll(Role role) {
		executeUpdate 'DELETE FROM PicUserRole WHERE role=:role', [role: role]
	}

	static mapping = {
		id composite: ['role', 'picUser']
		version false
	}
}
