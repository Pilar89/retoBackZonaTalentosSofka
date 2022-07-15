package co.com.sofka.tourFrancia.mappers;

import co.com.sofka.tourFrancia.collections.Cyclist;
import co.com.sofka.tourFrancia.model.CyclistDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CyclistMapper {

  public Function<CyclistDTO, Cyclist> cyclistDTOToCyclist(String id) {
    return updateCyclist -> {
      var cyclist = new Cyclist();
      cyclist.setId(id);
      cyclist.setName(updateCyclist.getName());
      cyclist.setTeamId(updateCyclist.getTeamId());
      cyclist.setNationality(updateCyclist.getNationality());
      return cyclist;
    };
  }

  public Function<Cyclist, CyclistDTO> cyclistToCyclistDTO() {
    return entity ->
        new CyclistDTO(
            entity.getId(), entity.getName(), entity.getTeamId(), entity.getNationality());
  }
}
