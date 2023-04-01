package manuall.altCrud.config

import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbcConfig {

    @Bean
    fun dataSource():DataSource {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
        dataSource.url = "jdbc:mysql://localhost:3306/altCrud"
        dataSource.username = "root"
        dataSource.password = "#Gf50069451842"
        return dataSource
    }
}