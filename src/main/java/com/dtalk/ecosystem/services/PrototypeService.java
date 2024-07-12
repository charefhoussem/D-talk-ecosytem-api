package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.entities.Prototype;

public interface PrototypeService {

    public Prototype createPrototype(Long idOrder);
    public Prototype updateEtat(Long idPrototype, String etat);
    public void validPrototype(Long idPrototype);


}
