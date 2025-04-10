package com.diamonddetail.api.controller;

import com.diamonddetail.api.entities.ServiceEntity;
import com.diamonddetail.api.record.services.ServiceResponseDTO;
import com.diamonddetail.api.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/create-service")
    public ResponseEntity<?> createService(@RequestBody ServiceEntity service){
        try{
            if(service.getName() == null || service.getName().isBlank()){
                return ResponseEntity.badRequest().body("Digite o nome do service!");
            }

            if(service.getName().length() < 3){
                return ResponseEntity.badRequest().body("O nome do serviço precisa ter mais de 3 caracteres!");
            }

            if(!service.getName().matches("^[a-zA-ZÀ-ÿ\\s]+$")){
                return ResponseEntity.badRequest().body("Nome do serviço não pode ter caracteres especiais!");
            }

            return ResponseEntity.ok(serviceRepository.save(service));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar serviço!");
        }
    }

    @GetMapping("/list-service")
    public ResponseEntity<?> listService(){
        try{
            List<ServiceEntity> services = serviceRepository.findAll();

            if(services.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum serviço encontrado!");
            }

            List<ServiceResponseDTO> response = services.stream().map(service -> new ServiceResponseDTO(
                    service.getId(),
                    service.getName(),
                    service.getPrice()
            )).toList();

            return ResponseEntity.ok(response);

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar serviços!");
        }
    }

}
