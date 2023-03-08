package tacos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.security.User;
import tacos.security.UserRepository;


@Configuration
public class SecurityConfig {

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			User user = userRepo.findByUsername(username);
			if(user != null) return user;
			
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			// BASIC LOGIN (for everything except the REST API):
			//	.authorizeRequests()
			//	.antMatchers("/design", "/orders").hasRole("USER")
			//	.anyRequest().permitAll()
			//.and()
			//	.formLogin()
			//		.loginPage("/login")
			//		.defaultSuccessUrl("/design") 
			
		    // OAUTH2 => only for REST API functionality
			.authorizeRequests()
		        .antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
		        .antMatchers(HttpMethod.POST, "/api/tacos")
		            .hasAuthority("SCOPE_writeTacos")
		        .antMatchers(HttpMethod.DELETE, "/api//tacos")
		            .hasAuthority("SCOPE_deleteTacos")
		        .antMatchers("/api//ingredients", "/api//orders/**")
		            .permitAll()
		        .antMatchers("/**").access("permitAll")
		    .and()
		        .oauth2ResourceServer(oauth2 -> oauth2.jwt())

		        .httpBasic()
		          .realmName("Taco Cloud")

			// Make H2-Console non-secured; for debug purposes
			.and()
	        	.csrf()
	        		.ignoringAntMatchers("/h2-console/**", "/api/**")
	        // Allow pages to be loaded in frames from the same origin; needed for H2-Console
	        .and()  
	        .headers()
	          .frameOptions()
	            .sameOrigin()
	            
	       .and()
	       .build();
	}
}
