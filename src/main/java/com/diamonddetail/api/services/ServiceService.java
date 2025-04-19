package com.diamonddetail.api.services;

import com.diamonddetail.api.record.services.ServiceCreateDTO;
import com.diamonddetail.api.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

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
}
