package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.paiement.UpdatePaiementRequest;
import com.dtalk.ecosystem.DTOs.response.PaymentResponse;
import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.payment;
import com.dtalk.ecosystem.exceptions.ResourceInvalidException;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.OrderRepository;
import com.dtalk.ecosystem.repositories.PaymentRepository;
import com.dtalk.ecosystem.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paiementRepository;
    private final OrderRepository orderRepository;
    @Autowired
    private RestTemplate restTemplate;



    public PaymentResponse getPaymentFromApi(String paymentId) {
        String url = "https://api.preprod.konnect.network/api/v2/payments/" + paymentId;
        return restTemplate.getForObject(url, PaymentResponse.class);
    }
    @Override
    public payment createPaiement(String paiement_ref, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("order not found " + orderId));
        if( paiementRepository.findById(paiement_ref).isPresent()){
            throw new ResourceNotFoundException("ref payment already exist");
        }

        String url = "https://api.preprod.konnect.network/api/v2/payments/" + paiement_ref;
        PaymentResponse response = restTemplate.getForObject(url, PaymentResponse.class);

        if(Objects.equals(response.getStatus(), "pending")){
          throw new ResourceInvalidException("The payment has failed or not attempted yet");
      }


        var isCompleted = false;
        if(order.getPaiements().stream().mapToDouble(payment::getAmount).sum() + response.getAmount() == order.getAmount() ) {
          isCompleted = true;
        }
        payment paiement = payment.builder().refPaiement(paiement_ref).amount(response.getAmount()).date(response.getExpirationDate()).isCompleted(isCompleted).order(order).build();

        return paiementRepository.save(paiement);
    }

    @Override
    public List<payment> getPaiementByOrder(Long idOrder) {
        Order order = orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found " + idOrder));
        return paiementRepository.findPaiementsByOrderEquals(order);
    }

    @Override
    public List<payment> getAllPaiement() {
        return paiementRepository.findAll();
    }

    @Override
    public payment updatePaiement(String refPaiement, UpdatePaiementRequest request) {
        payment paiement = paiementRepository.findById(refPaiement).orElseThrow(()-> new ResourceNotFoundException("paiement not found"));
        paiement.setModePaiement(request.getModePaiement());
        paiement.setDate(request.getDate());
        paiement.setAmount(request.getAmount());
        return paiementRepository.save(paiement);
    }

    @Override
    public void deletePaiement(String refPaiement) {
        payment paiement = paiementRepository.findById(refPaiement).orElseThrow(()-> new ResourceNotFoundException("paiement not found"));
        paiementRepository.delete(paiement);

    }
}
