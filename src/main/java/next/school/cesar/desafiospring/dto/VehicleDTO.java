package next.school.cesar.desafiospring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import next.school.cesar.desafiospring.entities.Vehicle;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    @NotBlank
    @Size(min = 1, max = 100)
    private String brand;
    @NotBlank
    @Size(min = 3, max = 100)
    private String model;
    @NotNull
    @Min(0)
    private int year;

    public Vehicle toEntity() {
        return Vehicle.builder()
                .brand(this.brand)
                .model(this.model)
                .year(this.year)
                .build();
    }


}
