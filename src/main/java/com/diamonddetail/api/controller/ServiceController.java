package com.diamonddetail.api.controller;

import com.diamonddetail.api.entities.ServiceEntity;
import com.diamonddetail.api.record.services.ServiceCreateDTO;
import com.diamonddetail.api.record.services.ServiceResponseDTO;
import com.diamonddetail.api.record.services.ServiceUpdateDTO;
import com.diamonddetail.api.record.users.UserUpdateDTO;
import com.diamonddetail.api.repository.ServiceRepository;
import com.diamonddetail.api.services.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
@Tag(name = "Serviços", description = "Operações relacionadas a serviços")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;


    @PostMapping("/create-service")
    @Operation(summary = "Cria um novo serviço", description = "Rota para criar um novo serviço no sistema")
    public ResponseEntity<?> createService(@RequestBody ServiceCreateDTO service){
        try{
            return ResponseEntity.ok(serviceService.createService(service));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar serviço!");
        }
    }

    @GetMapping("/list-service")
    @Operation(summary = "Lista os serviços cadastrados", description = "Rota para listar os serviços no sistema")
    public ResponseEntity<?> listService(){
        try{
            return ResponseEntity.ok(serviceService.listService());
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar serviços!");
        }
    }

    @PatchMapping("/update-service/{id}")
    @Operation(summary = "Altera os serviços cadastrados pelo id", description = "Rota para alterar os serviços no sistema")
    public ResponseEntity<?> updateService(@PathVariable Integer id, @RequestBody ServiceUpdateDTO serviceUpdateDTO){
        try{
            return ResponseEntity.ok(serviceService.updateService(id, serviceUpdateDTO));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar serviço!");
        }
    }


    @DeleteMapping("/delete-service/{id}")
    @Operation(summary = "Deleta o serviço pelo id", description = "Rota para deletar o serviço no sistema")
    public ResponseEntity<?> deleteService(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(serviceService.deleteService(id));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar serviço!");
        }
    }
}
