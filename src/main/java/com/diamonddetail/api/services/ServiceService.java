package com.diamonddetail.api.services;

import com.diamonddetail.api.entities.ServiceEntity;
import com.diamonddetail.api.record.services.ServiceCreateDTO;
import com.diamonddetail.api.record.services.ServiceResponseDTO;
import com.diamonddetail.api.record.services.ServiceUpdateDTO;
import com.diamonddetail.api.record.users.UserUpdateDTO;
import com.diamonddetail.api.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public String createService(@RequestBody ServiceCreateDTO service){

        if(service.name() == null || service.name().isBlank()){
            throw new IllegalArgumentException("Nome do serviço é obrigatório!");
        }

        if(!service.name().matches("^[a-zA-ZÀ-ÿ\\s]+$")){
            throw new IllegalArgumentException("Nome do serviço não pode ter caracteres especiais!");
        }

        if(service.name().length() < 3){
            throw new IllegalArgumentException("Nome do serviço precisa ter 3 ou mais caracteres!");
        }

        if(service.price() == null){
            throw new IllegalArgumentException("Serviço precisa ter um preço!");
        }

        if(service.price().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("O preço precisa ser um valor maior que zero!");
        }

        serviceRepository.save(service.toEntity());
        return "Serviço criado com sucesso!";
    }

    public List<ServiceResponseDTO> listService(){
        List<ServiceEntity> services = serviceRepository.findAll();

        if(services.isEmpty()){
            throw new IllegalArgumentException("Nenhum serviço encontrado!");
        }

        return services.stream().map(service -> new ServiceResponseDTO(
                service.getId(),
                service.getName(),
                service.getPrice()
        )).toList();
    }

    public String updateService(Integer id, ServiceUpdateDTO serviceUpdateDTO){
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado!"));

        if(serviceUpdateDTO.name() != null && !serviceUpdateDTO.name().isBlank()){
            if (!serviceUpdateDTO.name().matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
                throw new IllegalArgumentException("Nome do serviço não pode ter caracteres especiais!");
            }
            service.setName(serviceUpdateDTO.name());
        }

        if(serviceUpdateDTO.price() != null){
            if(serviceUpdateDTO.price().compareTo(BigDecimal.ZERO) < 0){
                throw new IllegalArgumentException("O preço precisa ser acima de 0!");
            }
            service.setPrice(serviceUpdateDTO.price());
        }

        serviceRepository.save(service);
        return "Serviço atualizado!";
    }

    public String deleteService(Integer id){
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado!"));

        serviceRepository.delete(service);
        return "Serviço deletado com sucesso!";
    }
}
