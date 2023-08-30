package next.school.cesar.desafiospring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import next.school.cesar.desafiospring.entities.Client;
import next.school.cesar.desafiospring.entities.House;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseUpdateDTO {

    @NotBlank
    @Size(min = 1, max = 100)
    private String ownership_status;
    @NotNull
    private long client_id;

    public House toEntity(House house, Client client) {
        return House.builder()
                .id(house.getId())
                .ownership_status(this.ownership_status)
                .location(house.getLocation())
                .zipcode(house.getZipcode())
                .client(client)
                .build();
    }


}
