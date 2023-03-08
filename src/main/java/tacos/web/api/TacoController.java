package tacos.web.api;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Taco;
import tacos.TacoOrder;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path="/api/tacos",
				produces="application/json")
@CrossOrigin(origins={"http://tacocloud:8080","http://localhost:8080/api/tacos"})
public class TacoController {
	private TacoRepository tacoRepo;
	
	public TacoController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}
	
	@GetMapping(params="recent")
	public Iterable<Taco> recentTacos() {
		PageRequest page = PageRequest.of(
				0, 12, Sort.by("createdAt").descending());
		return tacoRepo.findAll(page).getContent();
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco =  tacoRepo.findById(id);
		if(optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco) {
		return tacoRepo.save(taco);
	}
	
	@PutMapping(path="/{id}", consumes="application/json")
	public Taco putTaco(
				@PathVariable("id") Long tacoId,
				@RequestBody Taco taco) {
		taco.setId(tacoId);
		return tacoRepo.save(taco);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTaco(@PathVariable("id") Long tacoId) {
		try {
			tacoRepo.deleteById(tacoId);
		} catch (EmptyResultDataAccessException e) {}
	}
}
