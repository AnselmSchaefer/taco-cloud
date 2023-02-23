package tacos;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.domain.Persistable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class Ingredient implements Persistable<String> {

  @Id
  private String id;

  private String name;
  private Type type;

  @Override
	public boolean isNew() {
		return true;
	}

  public enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }

}
