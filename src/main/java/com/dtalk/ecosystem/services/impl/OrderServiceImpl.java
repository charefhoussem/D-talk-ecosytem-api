package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.*;
import com.dtalk.ecosystem.entities.users.Brand;
import com.dtalk.ecosystem.exceptions.IllegalStateExceptionHandler;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.*;
import com.dtalk.ecosystem.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BrandRepository brandRepository;
    private final DesignRepository designRepository;
    private final FolderStyleRepository folderStyleRepository;
    private final PrototypeRepository prototypeRepository;


    @Override
    public Order createOrderByDesign(Long idBrand, Long idDesign) {

       Brand brand = brandRepository.findById(idBrand).orElseThrow(()-> new ResourceNotFoundException("brand not found : "+ idBrand));
       Design design = designRepository.findById(idDesign).orElseThrow(()-> new ResourceNotFoundException("design not found : "+ idDesign));

       Order nOrder = new Order();
       nOrder.setBrand(brand);
       nOrder.getDesigns().add(design);
        return orderRepository.save(nOrder);
    }

    @Override
    public Order createOrderByFolderStyle(Long idBrand, Long idFolderStyle) {
        Brand brand = brandRepository.findById(idBrand).orElseThrow(()-> new ResourceNotFoundException("brand not found : "+ idBrand));
        FolderStyle folderStyle = folderStyleRepository.findById(idFolderStyle).orElseThrow(()-> new ResourceNotFoundException("folder not found : "+ idFolderStyle));

        Order nOrder = new Order();
        nOrder.setBrand(brand);
        nOrder.getFolderStyles().add(folderStyle);
        nOrder.setIsValid(true);
        Order order = orderRepository.save(nOrder);
        // prototype
        Prototype nProt = new Prototype();
        nProt.setOrder(order);
        prototypeRepository.save(nProt);
        return order;

    }

    @Override
    public void affectDesign(Long idOrder, Long idDesign) {
        Order order = orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found : "+ idOrder));
        Design design = designRepository.findById(idDesign).orElseThrow(()-> new ResourceNotFoundException("design not found : "+ idDesign));
        if (order.getDesigns().contains(design)) {
            throw new IllegalStateExceptionHandler("Design already exists in the order: " + idDesign);
        }
        order.getDesigns().add(design);
        orderRepository.save(order);
    }

    @Override
    public void affectFolderStyle(Long idOrder, Long idFolderStyle) {
        Order order = orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found : "+ idOrder));
        FolderStyle folderStyle = folderStyleRepository.findById(idFolderStyle).orElseThrow(()-> new ResourceNotFoundException("folder not found : "+ idFolderStyle));

        if (order.getFolderStyles().contains(folderStyle)) {
            throw new IllegalStateExceptionHandler("Folder style already exists in the order: " + idFolderStyle);
        }
        if(order.getFolderStyles().isEmpty()){

            Prototype nProt = Prototype.builder().order(order).build();
            prototypeRepository.save(nProt);
            order.setIsValid(true);
        }

        order.getFolderStyles().add(folderStyle);
        orderRepository.save(order);

    }

    @Override
    public void desaffecterDesign(Long idOrder, Long idDesign) {
        Order order = orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found : "+ idOrder));
        Design design = designRepository.findById(idDesign).orElseThrow(()-> new ResourceNotFoundException("design not found : "+ idDesign));
        if (!order.getDesigns().contains(design)) {
            throw new IllegalStateExceptionHandler("Design does not exists in the order: " + idDesign);
        }
        order.getDesigns().remove(design);
        orderRepository.save(order);
    }

    @Override
    public void desaffecterFolderStyle(Long idOrder, Long idFolderStyle) {
        Order order = orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found : "+ idOrder));
        FolderStyle folderStyle = folderStyleRepository.findById(idFolderStyle).orElseThrow(()-> new ResourceNotFoundException("folder not found : "+ idFolderStyle));
        if (!order.getFolderStyles().contains(folderStyle)) {
            throw new IllegalStateExceptionHandler("Folder style does not exists in the order: " + idFolderStyle);
        } if (order.getFolderStyles().size() == 1) {
            throw new IllegalStateExceptionHandler(" At least one FolderStyle must exist in the order. " );
        }
        order.getFolderStyles().remove(folderStyle);
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(long idOrder) {
        return orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found :  " +idOrder));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }

    @Override
    public void deleteOrder(long idOrder) {
        orderRepository.deleteById(idOrder);

    }
}
