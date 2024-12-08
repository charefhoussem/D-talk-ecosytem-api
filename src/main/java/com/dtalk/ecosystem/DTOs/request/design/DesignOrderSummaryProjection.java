package com.dtalk.ecosystem.DTOs.request.design;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DesignOrderSummaryProjection {
    String getDesignName();
    String getImagePath();
    LocalDateTime getOrderDate();
    LocalDate getCreationDate();
    long getOrderCount();
    int getTotalQuantity();
    Double getPriceDesign();
    double getTotalPrice();
}
