package co.com.sofka.tourFrancia.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
  private String id;

  @NotBlank(message = "El nombre del equipo es requerido")
  private String temaName;

  @NotBlank(message = "El país asociado es requerido")
  private String country;

  private List<CyclistDTO> cyclists;

  public TeamDTO(String id, String temaName, String country) {
    this.id = id;
    this.temaName = temaName;
    this.country = country;
  }

  public List<CyclistDTO> getCyclists() {
    this.cyclists = Optional.ofNullable(cyclists).orElse(new ArrayList<>());
    return cyclists;
  }
}
