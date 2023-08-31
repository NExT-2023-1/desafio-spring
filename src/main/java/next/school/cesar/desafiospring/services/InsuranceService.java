package next.school.cesar.desafiospring.services;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import next.school.cesar.desafiospring.dto.InsuranceDTO;
import next.school.cesar.desafiospring.dto.InsuranceHouseDTO;
import next.school.cesar.desafiospring.dto.InsuranceVehicleDTO;
import next.school.cesar.desafiospring.entities.Client;
import next.school.cesar.desafiospring.entities.House;
import next.school.cesar.desafiospring.entities.Insurance;
import next.school.cesar.desafiospring.entities.Vehicle;
import next.school.cesar.desafiospring.repositories.ClientRepository;
import next.school.cesar.desafiospring.repositories.HouseRepository;
import next.school.cesar.desafiospring.repositories.InsuranceRepository;
import next.school.cesar.desafiospring.repositories.VehicleRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;
    private final HouseRepository houseRepository;

    public Insurance createInsuranVehicle(InsuranceVehicleDTO insuranceVehicleDTO) {
        Client client = this.clientRepository.findById(insuranceVehicleDTO.getClient_id()).orElse(null);
        Vehicle vehicle = this.vehicleRepository.findById(insuranceVehicleDTO.getVehicle_id()).orElse(null);
        if (client != null && (vehicle != null && client.getVehicles().contains(vehicle))) {
            int risk = this.calculationRiskVehicle(insuranceVehicleDTO.getRisk_questions(), client, vehicle);
            Insurance insurance = Insurance.builder()
                    .client(client)
                    .type("auto")
                    .risk(risk)
                    .analysis(this.evaluateScore(risk))
                    .observation(insuranceVehicleDTO.getObservation())
                    .createdAt(LocalDate.now())
                    .validatedAt(LocalDate.now().plusDays(30))
                    .build();
            return this.insuranceRepository.save(insurance);
        }
        return null;
    }

    public Insurance createInsuranHouse(InsuranceHouseDTO insuranceHouseDTO) {
        Client client = this.clientRepository.findById(insuranceHouseDTO.getClient_id()).orElse(null);
        House house = this.houseRepository.findById(insuranceHouseDTO.getHouse_id()).orElse(null);
        if (client != null && (house != null && client.getHouses().contains(house))) {
            int risk = this.calculationRiskHouse(insuranceHouseDTO.getRisk_questions(), client, house);
            Insurance insurance = Insurance.builder()
                    .client(client)
                    .type("home")
                    .risk(risk)
                    .analysis(this.evaluateScore(risk))
                    .observation(insuranceHouseDTO.getObservation())
                    .createdAt(LocalDate.now())
                    .validatedAt(LocalDate.now().plusDays(30))
                    .build();
            return this.insuranceRepository.save(insurance);
        }
        return null;
    }

    public Insurance createInsuranDisability(InsuranceDTO insuranceDTO) {
        Client client = this.clientRepository.findById(insuranceDTO.getClient_id()).orElse(null);
        if (client != null && client.getIncome() > 0) {
            int risk = this.calculationRiskDisability(insuranceDTO.getRisk_questions(), client);
            Insurance insurance = Insurance.builder()
                    .client(client)
                    .type("disability")
                    .risk(risk)
                    .analysis(this.evaluateScore(risk))
                    .observation(insuranceDTO.getObservation())
                    .createdAt(LocalDate.now())
                    .validatedAt(LocalDate.now().plusDays(30))
                    .build();
            return this.insuranceRepository.save(insurance);
        }
        return null;
    }

    public Insurance createInsuranLife(InsuranceDTO insuranceDTO) {
        Client client = this.clientRepository.findById(insuranceDTO.getClient_id()).orElse(null);
        if (client != null && client.getAge() < 60) {
            int risk = this.calculationRiskLife(insuranceDTO.getRisk_questions(), client);
            Insurance insurance = Insurance.builder()
                    .client(client)
                    .type("life")
                    .risk(risk)
                    .analysis(this.evaluateScore(risk))
                    .observation(insuranceDTO.getObservation())
                    .createdAt(LocalDate.now())
                    .validatedAt(LocalDate.now().plusDays(30))
                    .build();
            return this.insuranceRepository.save(insurance);
        }
        return null;
    }

    private int calculationRiskLife(List<Boolean> risk_questions, Client client) {
        int risk = 0;
        risk += this.calculateCommunRisk(risk_questions, client);
        risk += this.calculateRiskPointsByClientHouses(client);
        risk += this.calculateRiskPointsByDependents(client);
        risk -= this.calculateRiskPointsByMaritalStatus(client);
        return risk;
    }

    private int calculationRiskDisability(List<Boolean> risk_questions, Client client) {
        int risk = 0;
        risk += this.calculateCommunRisk(risk_questions, client);
        risk += this.calculateRiskPointsByClientHouses(client);
        risk += this.calculateRiskPointsByDependents(client);
        risk -= this.calculateRiskPointsByMaritalStatus(client);
        return risk;
    }

    private int calculationRiskHouse(List<Boolean> risk_questions, Client client, House house) {
        int risk = 0;
        risk += this.calculateCommunRisk(risk_questions, client);
        risk += this.calculateRiskPointsByHouse(house);
        return risk;
    }

    private int calculationRiskVehicle(List<Boolean> risk_questions, Client client, Vehicle vehicle) {
        int risk = 0;
        risk += this.calculateCommunRisk(risk_questions, client);
        risk += this.calculateRiskPointsByVehicle(vehicle);
        return risk;
    }

    private int calculateCommunRisk(List<Boolean> risk_questions, Client client) {
        int risk = 0;
        risk += this.calculateBaseScore(risk_questions);
        risk += this.calculateRiskPointsByAge(client);
        risk += this.calculateRiskPointsByIncome(client);
        return risk;
    }

    private int calculateBaseScore(List<Boolean> risk_questions) {
        int sum = 0;
        for (Boolean risk : risk_questions)
            sum += risk ? 1 : 0;
        return sum;
    }

    private int calculateRiskPointsByAge(Client client) {
        if (client.getAge() < 30)
            return -2;
        else if (client.getAge() >= 30 && client.getAge() <= 40)
            return -1;
        else {
            return 0;
        }
    }

    private int calculateRiskPointsByHouse(House house) {
        if (house.getOwnership_status().equals("mortgaged"))
            return 1;
        return 0;
    }

    private int calculateRiskPointsByClientHouses(Client client) {
        int risk = 0;
        for (House house : client.getHouses())
            risk += this.calculateRiskPointsByHouse(house);
        return risk;
    }

    private int calculateRiskPointsByIncome(Client client) {
        if (client.getIncome() > 200000)
            return -1;
        return 0;
    }

    private int calculateRiskPointsByVehicle(Vehicle vehicle) {
        if (vehicle.getYear() >= Calendar.getInstance().get(Calendar.YEAR) - 5)
            return 1;
        return 0;
    }

    private int calculateRiskPointsByDependents(Client client) {
		if (client.getDependents() > 0)
			return 1;
        return 0;
	}

    private int calculateRiskPointsByMaritalStatus(Client client) {

		if (client.getMarital_status().equals("married"))
			return 1;
        return 0;
	}

    private String evaluateScore(int score) {
        if (score <= 0)
            return "ECONOMIC";
        else if (score >= 1 && score <= 2)
            return "REGULAR";
        else
            return "RESPONSIBLE";
    }
}
