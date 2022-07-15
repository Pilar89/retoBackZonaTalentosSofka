package co.com.sofka.tourFrancia.collections;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "teams")
public class Team {
  @Id private String id;
  private String temaName;
  private String country;
}
