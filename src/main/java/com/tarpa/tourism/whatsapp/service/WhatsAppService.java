package com.tarpa.tourism.whatsapp.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.sender}")
    private String senderNumber;

    // This initializes the Twilio connection the moment your app starts
    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendWhatsAppMessage(String clientPhone, String messageBody) {
        try {
            // Twilio requires WhatsApp numbers to be prefixed with "whatsapp:"
            String toWhatsApp = "whatsapp:" + clientPhone;
            String fromWhatsApp = "whatsapp:" + senderNumber;

            Message message = Message.creator(
                    new PhoneNumber(toWhatsApp),
                    new PhoneNumber(fromWhatsApp),
                    messageBody
            ).create();

            System.out.println("WhatsApp message sent successfully! SID: " + message.getSid());

        } catch (Exception e) {
            System.err.println("Failed to send WhatsApp message: " + e.getMessage());
        }
    }
}