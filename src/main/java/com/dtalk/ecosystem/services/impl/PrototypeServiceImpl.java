package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.Prototype;
import com.dtalk.ecosystem.entities.enumiration.EtatOrder;
import com.dtalk.ecosystem.entities.enumiration.EtatPrototype;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.OrderRepository;
import com.dtalk.ecosystem.repositories.PrototypeRepository;
import com.dtalk.ecosystem.services.PrototypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrototypeServiceImpl implements PrototypeService {
    private final PrototypeRepository prototypeRepository;
    private final OrderRepository orderRepository;
    @Override
    public Prototype createPrototype(Long idOrder) {
        Order order =  orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found :  " +idOrder));
        Prototype np = Prototype.builder().order(order).build();
        return prototypeRepository.save(np);
    }

    @Override
    public Prototype updateEtat(Long idPrototype, String etat) {
        Prototype prototype =  prototypeRepository.findById(idPrototype).orElseThrow(()-> new ResourceNotFoundException("prototype not found :  " +idPrototype));
        prototype.setEtat(EtatPrototype.valueOf(etat));
        return prototypeRepository.save(prototype) ;
    }

    @Override
    public void validPrototype(Long idPrototype) {
        Prototype prototype =  prototypeRepository.findById(idPrototype).orElseThrow(()-> new ResourceNotFoundException("prototype not found :  " +idPrototype));
        prototype.setIsValid(true);
        prototype.getOrder().setEtat(EtatOrder.PRODUCTION);
        prototypeRepository.save(prototype) ;

    }
}
