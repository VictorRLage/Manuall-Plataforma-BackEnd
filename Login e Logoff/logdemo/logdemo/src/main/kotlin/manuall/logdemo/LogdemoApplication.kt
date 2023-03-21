package manuall.logdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LogdemoApplication

fun main(args: Array<String>) {
	runApplication<LogdemoApplication>(*args)
}