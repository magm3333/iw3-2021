package ar.edu.iua.iw3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.edu.iua.auth.CustomTokenAuthenticationFilter;
import ar.edu.iua.iw3.modelo.cuentas.IUserNegocio;
import ar.edu.iua.iw3.security.authtoken.IAuthTokenBusiness;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private IAuthTokenBusiness authTokenBusiness;

	@Autowired
	private IUserNegocio userBusiness;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication()
		//	.withUser("user").password( passwordEncoder().encode("123") )
		//		.roles("USER")
		//	.and()
		//	.withUser("admin").password( passwordEncoder().encode("123") )
		//		.roles("USER","ADMIN");
		
		auth.userDetailsService(userDetailService);

	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		
		//http.httpBasic();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/login*").permitAll();
		http.authorizeRequests().antMatchers("/index.html").permitAll();
		http.authorizeRequests().antMatchers("/favicon.png").permitAll();
		http.authorizeRequests().antMatchers("/ui/**").permitAll();
		
		//http.authorizeRequests().antMatchers("/productos*").permitAll(); //.hasRole("ADMIN");
		
		http.authorizeRequests().antMatchers("/test*").hasAnyRole("ADMIN","USER");
		
		http.authorizeRequests().anyRequest().authenticated();
		
		http.addFilterAfter(new CustomTokenAuthenticationFilter(authTokenBusiness, userBusiness),UsernamePasswordAuthenticationFilter.class);
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		/*
		http.formLogin().defaultSuccessUrl("/ui/index.html")
			.and().logout().deleteCookies("JSESSIONID", "rememberme-iw3");
		
		http.rememberMe().rememberMeCookieName("rememberme-iw3").alwaysRemember(true);
		*/

	}
	
}

/*
 * Cliente envia credenciales (username+password) --------> Backend (comprobar que esté todo OK)
 *                                                                 generar token 
 *         <--------respose(token)---------------------- retornar token
 * 
 *   ----------------------request (token) -----------------------> Backend (comprueba token)
 *                                                                     obtiene user asociado al token
 *                                                                     se fija si user tiene permiso para consumir API
 *                                                                     consume API
 *   <------------------------------------------------------respose (resultados)
 *    *   ----------------------request (token) -----------------------> Backend (comprueba token)
 *                                                                     obtiene user asociado al token
 *                                                                     se fija si user tiene permiso para consumir API
 *                                                                     consume API
 *   <------------------------------------------------------respose (resultados)
 *    *   ----------------------request (token) -----------------------> Backend (comprueba token)
 *                                                                     obtiene user asociado al token
 *                                                                     se fija si user tiene permiso para consumir API
 *                                                                     consume API
 *   <------------------------------------------------------respose (resultados)
 *    *   ----------------------request (token) -----------------------> Backend (comprueba token)
 *                                                                     obtiene user asociado al token
 *                                                                     se fija si user tiene permiso para consumir API
 *                                                                     consume API
 *   <------------------------------------------------------respose (resultados)
 */

//Cliente (Browser) ---> Request -----------------------------> (Filtro+UsernamePasswordAuthenticationFilter+CustomFiltro) Backend
//                                                                         Procesa
//                  <--------------------------------- response 



