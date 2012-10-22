import com.futurenowsoftwaredesign.picturethis.PicUser
import com.futurenowsoftwaredesign.picturethis.PicUserRole
import com.futurenowsoftwaredesign.picturethis.Role

class BootStrap {
//    def springSecurityService
    def init = { servletContext ->
         //password is automatically encoded on save
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

        def testUser = new PicUser(username: 'testUser', enabled: true, password: 'testUser')
        testUser.save flush: true

        def testUser2 = new PicUser(username: 'testUser2', enabled: true, password: 'testUser2')
        testUser2.save flush: true


        PicUserRole.create testUser, adminRole, true
        PicUserRole.create testUser2, userRole, true
        assert PicUser.count() == 2
        assert Role.count() == 2
        assert PicUserRole.count() == 2

        }

    def destroy = {
    }
}
