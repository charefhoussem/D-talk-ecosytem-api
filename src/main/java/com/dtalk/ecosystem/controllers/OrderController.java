package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PreAuthorize("hasRole('BRAND')")

    @PostMapping("/add-by-design/{idBrand}/{idDesign}")
    public ResponseEntity<Order> createOrderByDesign(@PathVariable("idBrand") Long idBrand ,@PathVariable("idDesign") Long idDesign ){
        return ResponseEntity.ok(orderService.createOrderByDesign(idBrand,idDesign));
    }
    @PreAuthorize("hasRole('BRAND')")

    @PostMapping("/add-by-folder-style/{idBrand}/{idFolder}")
    public ResponseEntity<Order> createOrderByFolder(@PathVariable("idBrand") Long idBrand ,@PathVariable("idFolder") Long idFolder ){
        return ResponseEntity.ok(orderService.createOrderByFolderStyle(idBrand,idFolder));
    }
    @PreAuthorize("hasRole('BRAND')")

    @PutMapping("/affect-design/{idOrder}/{idDesign}")
    public ResponseEntity<Order> affectDesign(@PathVariable("idOrder") Long idOrder ,@PathVariable("idDesign") Long idDesign ){
        return ResponseEntity.ok(orderService.affectDesign(idOrder,idDesign));
    }
    @PreAuthorize("hasRole('BRAND')")

    @PutMapping("/affect-folder-style/{idOrder}/{idFolder}")
    public ResponseEntity<Order> affectFolder(@PathVariable("idOrder") Long idOrder ,@PathVariable("idFolder") Long idFolder ){
        return ResponseEntity.ok(orderService.affectFolderStyle(idOrder,idFolder));
    }
    @PreAuthorize("hasRole('BRAND')")

    @PutMapping("/desaffect-design/{idOrder}/{idDesign}")
    public ResponseEntity<Order> desaffectDesign(@PathVariable("idOrder") Long idOrder ,@PathVariable("idDesign") Long idDesign ){
        return ResponseEntity.ok(orderService.desaffecterDesign(idOrder,idDesign));
    }
    @PreAuthorize("hasRole('BRAND')")

    @PutMapping("/desaffect-folder-style/{idOrder}/{idFolder}")
    public ResponseEntity<Order> desaffectFolder(@PathVariable("idOrder") Long idOrder ,@PathVariable("idFolder") Long idFolder ){
        return ResponseEntity.ok(orderService.desaffecterFolderStyle(idOrder,idFolder));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public List<Order> getAllOrder(){
        return orderService.getAllOrders();
    }

    @PreAuthorize("hasRole('BRAND')")

    @GetMapping("/brand/{idBrand}")
    public List<Order> getAllOrdersByBrand(@PathVariable("idBrand") Long idBrand){
        return orderService.getOrdersByBrand(idBrand);
    }
    @GetMapping("/{idOrder}")
    public Order getAllOrder(@PathVariable("idOrder") Long idOrder){
        return orderService.getOrderById(idOrder);
    }

    @DeleteMapping("/delete/{idOrder}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("idOrder") Long idOrder){
        orderService.deleteOrder(idOrder);
        return ResponseEntity.ok("order deleted successfuly");

    }



}
