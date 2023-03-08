package tacos;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}
	
	@Bean 
	public CommandLineRunner dataLoader(IngredientRepository repo, TacoRepository tacoRepo) { 
	  return args -> { 

		  repo.deleteAll(); // TODO: Quick hack to avoid tests from stepping on each other with constraint violations
		  Ingredient flto = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
	      repo.save(flto);
	      repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
	      repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
	      repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
	      repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
	      repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
	      repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
	      repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
	      repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
	      repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
	      Taco taco1 = new Taco();
	      taco1.setName("Veggie");
	      taco1.setIngredients(Arrays.asList(flto));
	      tacoRepo.save(taco1);

	  }; 
	} 
}
