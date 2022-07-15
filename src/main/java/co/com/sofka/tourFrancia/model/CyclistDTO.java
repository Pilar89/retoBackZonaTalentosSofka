package co.com.sofka.tourFrancia.model;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CyclistDTO {

  private String id;

  @NotBlank(message = "El nombre del ciclista es requerido")
  private String name;

  @NotBlank private String teamId;

  @NotBlank(message = "La nacionalidad es requerida")
  private String nationality;
}
