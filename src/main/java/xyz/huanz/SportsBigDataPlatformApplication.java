package xyz.huanz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages="xyz.huanz.mapper")
@ComponentScan(basePackages= {"xyz.huanz","org.n3r.idworker"})
public class SportsBigDataPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsBigDataPlatformApplication.class, args);
	}

}
