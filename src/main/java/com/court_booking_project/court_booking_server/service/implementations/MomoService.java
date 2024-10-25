package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.config.MomoSettings;
import com.court_booking_project.court_booking_server.dto.Request.momo.ExtraData;
import com.court_booking_project.court_booking_server.dto.Request.momo.MomoCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.Request.momo.MomoRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.dto.Request.momo.PaymentRequestData;
import com.court_booking_project.court_booking_server.entity.Reservation;
import com.court_booking_project.court_booking_server.utils.momo.CreateSignature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class MomoService {
        MomoSettings momoSettings;
        RestTemplate restTemplate;
        ObjectMapper objectMapper;
        CreateSignature createSignature;

        public MomoCreatePaymentDTO createPayment(Reservation reservation, MomoRequestCreatePaymentDTO request) throws Exception {

                String reservationInfo = "Payment for the reservation " + reservation.getCreatedAt();
                String requestId = UUID.randomUUID().toString();
                // get current milliseconds
                String orderId = System.currentTimeMillis() + "_" + reservation.getId();
                String notifyUrl = momoSettings.getNotifyUrl().replace("{{id}}", String.valueOf(reservation.getId()));

                // Create extraData
                String extraData = Base64.getEncoder().encodeToString(objectMapper.writeValueAsBytes(new ExtraData(reservation.getId())));

                // Build rawData
                String rawData = String.format("accessKey=%s&amount=%.0f&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s" +
                                        "&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                                momoSettings.getAccessKey(),
                                reservation.getTotalPrice(),
                                extraData,
                                notifyUrl,
                                orderId,
                                reservationInfo,
                                momoSettings.getPartnerCode(),
                                momoSettings.getReturnUrl(),
                                requestId,
                                request.getRequestType());

                // Create signature
                String signature = createSignature.computeHmacSha256(rawData, momoSettings.getSecretKey());

                // Build request data
                PaymentRequestData requestData = new PaymentRequestData(
                                momoSettings.getPartnerCode(),
                                requestId,
                                reservation.getTotalPrice(),
                                orderId,
                                reservationInfo,
                                momoSettings.getReturnUrl(),
                                notifyUrl,
                                "vi",
                                15,
                                extraData,
                                request.getRequestType(),
                                signature,
                                true
                );

                // Prepare HTTP request
                HttpHeaders headers = new HttpHeaders();
                // set content type JSON
                headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                // Create the HTTP entity with the request data and headers
                HttpEntity<PaymentRequestData> httpEntity = new HttpEntity<>(requestData, headers);

                try {
                        // Make the HTTP POST request to the Momo API and capture the response
                        ResponseEntity<MomoCreatePaymentDTO> response = restTemplate.exchange(
                                        momoSettings.getMomoApiUrl(),
                                        HttpMethod.POST,
                                        httpEntity,
                                        MomoCreatePaymentDTO.class
                        );

                        if (response.getStatusCode().is2xxSuccessful()) {
                                return response.getBody();
                        } else {
                                System.out.println("Error: " + response.getStatusCode());
                                throw new Exception("Failed to create Momo payment: " + Objects.requireNonNull(response.getBody()).getMessage());
                        }
                } catch (Exception ex) {
                        System.out.println("Exception: " + ex.getMessage());
                        throw ex;
                }
        }

//        // Method to compute HMAC-SHA256 signature
//        private String computeHmacSha256(String rawData, String secretKey) throws Exception {
//                Mac hmac = Mac.getInstance("HmacSHA256"); // Create an HMAC instance for SHA256
//                SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"); // Create a key specification
//                hmac.init(keySpec); // Initialize the HMAC with the secret key
//                byte[] hash = hmac.doFinal(rawData.getBytes(StandardCharsets.UTF_8)); // Compute the HMAC
//                return bytesToHex(hash); // Convert the byte array to a hex string and return
//        }
//
//        // Convert byte array to hex string
//        private String bytesToHex(byte[] bytes) {
//                StringBuilder hexString = new StringBuilder(); // Create a StringBuilder to hold the hex string
//                for (byte b : bytes) { // Iterate over each byte
//                        String hex = Integer.toHexString(0xFF & b); // Convert byte to hex
//                        if (hex.length() == 1) hexString.append('0'); // Pad with leading zero if necessary
//                        hexString.append(hex); // Append the hex value to the string
//                }
//                return hexString.toString(); // Return the final hex string
//        }
}
