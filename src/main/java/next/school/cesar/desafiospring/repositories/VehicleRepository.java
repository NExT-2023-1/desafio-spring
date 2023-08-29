package next.school.cesar.desafiospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import next.school.cesar.desafiospring.entities.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
    
}
