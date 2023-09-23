package manuall.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*


@Configuration
class LocaleConfig {

    @Bean
    fun localeResolver(): SessionLocaleResolver {
        val slr = SessionLocaleResolver()
        slr.setDefaultLocale(Locale("pt", "BR"))
        return slr
    }
}