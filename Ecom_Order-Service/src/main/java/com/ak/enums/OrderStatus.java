package com.ak.enums;

public enum OrderStatus {
	
	PLACED,              // Just created (0 seconds)
    CONFIRMED,           // After 5 seconds
    PROCESSING,          // After 10 seconds  
    OUT_FOR_DELIVERY,    // After 20 seconds
    DELIVERED            // After 30 seconds

}
