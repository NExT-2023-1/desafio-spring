package next.school.cesar.desafiospring.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private int dependents;
    @Column(nullable = false)
    private double income;
    @Column(nullable = false)
    private String marital_status;
    @Column(nullable = false)
    private LocalDate createdAt;
    @Column(nullable = false)
    private LocalDate updatedAt;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "client")
    private List<Vehicle> vehicles;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "client")
    private List<House> houses;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "client")
    private List<Insurance> insurances;
}
