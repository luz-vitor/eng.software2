package br.unesp.rc.springtutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringtutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringtutorialApplication.class, args);
	}

	// @Override
	// public void run(String... args) throws Exception {
	// Fisica entity = InstanceGenerator.getPessoaFisica("111.222.333-44", "user1");
	// System.out.println("\n" + entity + "\n");
	// }

}
