package com.diamonddetail.api.controller;

import com.diamonddetail.api.entities.ServiceEntity;
import com.diamonddetail.api.record.services.ServiceResponseDTO;
import com.diamonddetail.api.record.services.ServiceUpdateDTO;
import com.diamonddetail.api.record.users.UserUpdateDTO;
import com.diamonddetail.api.repository.ServiceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
@Tag(name = "Serviços", description = "Operações relacionadas a serviços")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;


    @PostMapping("/create-service")
    @Operation(summary = "Cria um novo serviço", description = "Rota para criar um novo serviço no sistema")
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
    @Operation(summary = "Lista os serviços cadastrados", description = "Rota para listar os serviços no sistema")
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

    @PutMapping("/update-service/{id}")
    @Operation(summary = "Altera os serviços cadastrados pelo id", description = "Rota para alterar os serviços no sistema")
    public ResponseEntity<?> updateService(@PathVariable Integer id, @RequestBody ServiceUpdateDTO serviceUpdateDTO){
        try{
            Optional<ServiceEntity> optionalService = serviceRepository.findById(id);

            if(optionalService.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado!");
            }

            ServiceEntity service = optionalService.get();

            if(serviceUpdateDTO != null && !serviceUpdateDTO.name().isBlank()){
                if(!serviceUpdateDTO.name().matches("^[a-zA-ZÀ-ÿ\\\\s]+$")){
                    return ResponseEntity.badRequest().body("Serviço não pode ter caracteres especiais!");
                }

                service.setName(serviceUpdateDTO.name());
            }

            if(serviceUpdateDTO != null){
                service.setPrice(serviceUpdateDTO.price());
            }

            serviceRepository.save(service);
            return ResponseEntity.ok("Serviço atualizado com sucesso!");
            
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar serviço!");
        }
    }

    @PatchMapping("/update-patch-service/{id}")
    @Operation(summary = "Altera o serviço cadastrado pelo id", description = "Rota para alterar os serviços no sistema")
    public ResponseEntity<?> updateServicePatch(@PathVariable Integer id, @RequestBody ServiceUpdateDTO serviceUpdateDTO){
        try{
            Optional<ServiceEntity> optionalService = serviceRepository.findById(id);

            if(optionalService.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado!");
            }

            ServiceEntity service = optionalService.get();

            if(serviceUpdateDTO != null && !serviceUpdateDTO.name().isBlank()){
                if(!serviceUpdateDTO.name().matches("^[a-zA-ZÀ-ÿ\\\\s]+$")){
                    return ResponseEntity.badRequest().body("Serviço não pode ter caracteres especiais!");
                }

                service.setName(serviceUpdateDTO.name());
            }

            if(serviceUpdateDTO != null){
                service.setPrice(serviceUpdateDTO.price());
            }

            serviceRepository.save(service);
            return ResponseEntity.ok("Serviço atualizado com sucesso!");


        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao alterar o usuário");
        }
    }

    @DeleteMapping("/delete-service/{id}")
    @Operation(summary = "Deleta o serviço pelo id", description = "Rota para deletar o serviço no sistema")
    public ResponseEntity<?> deleteService(@PathVariable Integer id){
        Optional<ServiceEntity> optionalService = serviceRepository.findById(id);

        if(optionalService.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado!");
        }

        ServiceEntity service = optionalService.get();
        if(!service.getId().equals(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID do serviço não confere!");
        }

        serviceRepository.deleteById(id);
        return ResponseEntity.ok("Serviço deletado com sucesso!");

    }
}
