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
public class HouseDTO {

    @NotBlank
    @Size(min = 1, max = 100)
    private String ownership_status;
    @NotBlank
    @Size(min = 3, max = 100)
    private String location;
    @NotBlank
    @Size(min = 9, max = 9)
    private String zipcode;
    @NotNull
    private long client_id;

    public House toEntity(Client client) {
        return House.builder()
                .ownership_status(this.ownership_status)
                .location(this.location)
                .zipcode(this.zipcode)
                .client(client)
                .build();
    }


}
