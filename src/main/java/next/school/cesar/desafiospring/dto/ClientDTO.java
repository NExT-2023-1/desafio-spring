package next.school.cesar.desafiospring.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import next.school.cesar.desafiospring.entities.Client;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
    @NotNull
    @Min(0)
    private int age;
    @NotNull
    @Min(0)
    private int dependents;
    @NotNull
    @Min(0)
    private double income;
    @NotBlank
    private String maritalStatus;

    public Client toEntity() {
        return Client.builder()
                .name(this.name)
                .age(this.age)
                .dependents(this.dependents)
                .income(this.income)
                .marital_status(this.maritalStatus)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();
    }

    public Client toEntityUpdate(Client client) {
        return Client.builder()
                .id(client.getId())
                .name(this.name)
                .age(this.age)
                .dependents(this.dependents)
                .income(this.income)
                .marital_status(this.maritalStatus)
                .houses(client.getHouses())
                .vehicles(client.getVehicles())
                .createdAt(client.getCreatedAt())
                .updatedAt(LocalDate.now())
                .build();
    }
}
