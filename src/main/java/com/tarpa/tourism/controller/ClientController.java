package com.tarpa.tourism.controller;

import com.tarpa.tourism.request.ClientRequest;
import com.tarpa.tourism.response.ClientResponse;
import com.tarpa.tourism.service.ClientService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    // ==========================
    // CREATE CLIENT
    // ==========================

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(
            @Valid @RequestBody ClientRequest request) {

        ClientResponse response =
                clientService.createClient(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================
    // GET ALL CLIENTS
    // ==========================

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {

        return ResponseEntity.ok(
                clientService.getAllClients()
        );
    }

    // ==========================
    // GET CLIENT BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                clientService.getClientById(id)
        );
    }

    // ==========================
    // UPDATE CLIENT
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequest request) {

        return ResponseEntity.ok(
                clientService.updateClient(id, request)
        );
    }

    // ==========================
    // DELETE CLIENT
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @PathVariable Long id) {

        clientService.deleteClient(id);

        return ResponseEntity.noContent().build();
    }
}