package ru.kpfu.itis.asadullin.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.kpfu.itis.asadullin.util.constants.ServerResources;
import ru.kpfu.itis.asadullin.util.handlers.ResponseAuthenticationHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ComponentScan("ru.kpfu.itis.asadullin.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DaoAuthenticationProvider provider;
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    private final ResponseAuthenticationHandler authenticationHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/profile",
                        "/favourite",
                        "/suggest",
                        "/find_friends"
                ).hasAnyAuthority(
                        ServerResources.USER_AUTHORITY,
                        ServerResources.ADMIN_AUTHORITY
                )
                .anyRequest().permitAll();

        http.csrf().disable()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("/login")
                .passwordParameter(ServerResources.PASSWORD)
                .defaultSuccessUrl(ServerResources.HOME_URL, true)
                .failureUrl("/login?error=true")
                .failureHandler(authenticationHandler)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies(ServerResources.CURRENT_USER, ServerResources.SESSION_ID_KEY)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                .userDetailsService(userDetailsService)
                .tokenRepository(persistentTokenRepository())
                .rememberMeParameter(ServerResources.REMEMBER_ME)
                .and()
                .exceptionHandling();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
