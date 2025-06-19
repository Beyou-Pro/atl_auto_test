package com.ecommerce.entity.payment;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Payment {
    @Id
    private String id;

    private String order_id;
    private String customer_id;
    private String currency;
    private String method;
    private String status;
    private Date transaction_date;
    private String processor_response;
    private String notes;
}
