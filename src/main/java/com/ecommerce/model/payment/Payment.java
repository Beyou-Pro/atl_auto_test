package com.ecommerce.model.payment;

import java.util.Date;

public class Payment {
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
