package co.com.sofka.tourFrancia.collections;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cyclists")
public class Cyclist {

  @Id private String id;
  private String name;
  private String teamId;
  private String nationality;
}
