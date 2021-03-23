package com.brub.ticketer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean("encoder")
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//https://stackoverflow.com/questions/4892070/spring-security-db-authorization-with-a-default-authority
		auth
				.userDetailsService(authenticationService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Configuration
	@Order(1)
	public static class StudentSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			 http.antMatcher("/student/**")
					.authorizeRequests()
					.antMatchers("/resources/static/**", "/css/**").permitAll()
					 .antMatchers(HttpMethod.GET,"/student/form_student").permitAll()
					 .antMatchers(HttpMethod.POST, "/student/new").permitAll()
					.anyRequest().hasRole("USER")
					.and()
					.formLogin(form -> form
							.loginPage("/student/login")
							.defaultSuccessUrl("/student/dashboard", true)
							.permitAll()
					).csrf().disable();
		}
	}

	@Configuration
	@Order(2)
	public static class AgentSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			  http.antMatcher("/agent/**")
					.authorizeRequests()
					.antMatchers("/resources/static/**", "/css/**").permitAll()
					.antMatchers(HttpMethod.GET, "/agent/login").permitAll()
					.antMatchers(HttpMethod.POST, "/agent/login").permitAll()
					.anyRequest().hasRole("ADMIN")
					.and()
					.formLogin(form -> form
							.loginPage("/agent/login")
							.defaultSuccessUrl("/agent/dashboard", true)
							.permitAll()
					).csrf().disable();
		}


	}

	@Configuration
	@Order(3)
	public static class SecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.authorizeRequests()
					.antMatchers("/resources/static/**", "/css/**").permitAll()
					.antMatchers("/login").permitAll()
					.antMatchers(HttpMethod.POST, "/ticket/save_ticket").hasRole("USER")
					.antMatchers(HttpMethod.GET, "/ticket/form_ticket").hasRole("USER")
					.anyRequest().hasAnyRole("ADMIN", "USER")
					.and()
					.formLogin(form -> form
							.loginPage("/")
							.defaultSuccessUrl("/student/dashboard", true)
							.permitAll()
					).csrf().disable().logout().logoutSuccessUrl("/");
		}
	}


}
