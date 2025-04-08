package com.diamonddetail.api.controller;

import com.diamonddetail.api.entities.ServiceEntity;
import com.diamonddetail.api.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/create-service")
    public ResponseEntity<?> createService(@RequestBody ServiceEntity service){
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
    }

}
