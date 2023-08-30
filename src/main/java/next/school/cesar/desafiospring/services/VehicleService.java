package next.school.cesar.desafiospring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import next.school.cesar.desafiospring.dto.VehicleDTO;
import next.school.cesar.desafiospring.dto.VehicleUpdateDTO;
import next.school.cesar.desafiospring.entities.Client;
import next.school.cesar.desafiospring.entities.Vehicle;
import next.school.cesar.desafiospring.repositories.ClientRepository;
import next.school.cesar.desafiospring.repositories.VehicleRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;

    public Vehicle create(VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleDTO.toEntity();
        return this.vehicleRepository.save(vehicle);
    }

    public Vehicle update(long id, @Valid VehicleUpdateDTO vehicleUpdateDTO) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElse(null);
        Client client = this.clientRepository.findById(vehicleUpdateDTO.getClient_id()).orElse(null);
        if (client != null && vehicle != null){
            vehicle.setClient(client);
            return this.vehicleRepository.save(vehicle);
        }
        return null;
    }

    public boolean delete(long id) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElse(null);
        if (vehicle != null){
            vehicle.getClient().getVehicles().remove(vehicle);
            vehicle.setClient(null);
            vehicleRepository.save(vehicle);
            this.vehicleRepository.delete(vehicle);
            return true;
        }
        return false;
    }
}
