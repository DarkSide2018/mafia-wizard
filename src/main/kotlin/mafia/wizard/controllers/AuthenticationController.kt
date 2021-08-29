package mafia.wizard.controllers

import mafia.wizard.config.JWTTokenHelper
import mafia.wizard.entities.User
import mafia.wizard.requests.AuthenticationRequest
import mafia.wizard.responses.LoginResponse
import mafia.wizard.responses.UserInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import java.security.NoSuchAlgorithmException
import java.security.Principal
import java.security.spec.InvalidKeySpecException

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class AuthenticationController {
    @Autowired
    lateinit var  authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jWTTokenHelper: JWTTokenHelper

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @PostMapping("/auth/login")
    @Throws(InvalidKeySpecException::class, NoSuchAlgorithmException::class)
    fun login(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<*>? {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.userName, authenticationRequest.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val user: User = authentication.principal as User
        val jwtToken = jWTTokenHelper.generateToken(user.getUsername())
        val response = LoginResponse()
        response.token = jwtToken
        return ResponseEntity.ok(response)
    }

    @GetMapping("/auth/userinfo")
    fun getUserInfo(user: Principal): ResponseEntity<*>? {
        val userObj: User = userDetailsService.loadUserByUsername(user.name) as User
        val userInfo = UserInfo()
        userInfo.firstName = userObj.firstName
        userInfo.lastName = userObj.lastName
        userInfo.roles = userObj.authorities
        return ResponseEntity.ok(userInfo)
    }
}