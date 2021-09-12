package mafia.wizard.config

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    private var userDetailsService: UserDetailsService,
    private var jwtTokenHelper: JWTTokenHelper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authToken = jwtTokenHelper.getToken(request)
        val username = jwtTokenHelper.getUsernameFromToken(authToken)
                val userDetails: UserDetails = userDetailsService.loadUserByUsername(username);

                if (jwtTokenHelper.validateToken(authToken, userDetails)) {

                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    authentication.details = WebAuthenticationDetails(request);
                    SecurityContextHolder.getContext().authentication = authentication;
                }


        filterChain.doFilter(request, response);
    }
}
