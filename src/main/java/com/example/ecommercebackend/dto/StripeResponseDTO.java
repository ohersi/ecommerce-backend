package com.example.ecommercebackend.dto;

public class StripeResponseDTO {
    private String session_url;

    public StripeResponseDTO(String session_url) {
        this.session_url = session_url;
    }

    public String getSession_url() {
        return session_url;
    }
}
