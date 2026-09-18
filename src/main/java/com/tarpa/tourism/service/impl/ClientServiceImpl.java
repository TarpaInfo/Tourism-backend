package com.tarpa.tourism.service.impl;

import com.tarpa.tourism.entity.Client;
import com.tarpa.tourism.repository.ClientRepository;
import com.tarpa.tourism.request.ClientRequest;
import com.tarpa.tourism.response.ClientResponse;
import com.tarpa.tourism.service.ClientService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientResponse createClient(ClientRequest request) {

        // Check duplicate email
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(
                    "Client with this email already exists"
            );
        }

        // Check duplicate passport
        if (request.getPassportNumber() != null &&
                !request.getPassportNumber().isBlank() &&
                clientRepository
                        .findByPassportNumber(request.getPassportNumber())
                        .isPresent()) {

            throw new RuntimeException(
                    "Client with this passport number already exists"
            );
        }

        Client client = new Client();

        mapRequestToEntity(request, client);

        client.setActive(true);

        Client savedClient =
                clientRepository.save(client);

        return mapEntityToResponse(savedClient);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse getClientById(Long id) {

        Client client =
                clientRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Client not found with id: " + id
                                )
                        );

        return mapEntityToResponse(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> getAllClients() {

        return clientRepository.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .toList();
    }

    @Override
    public ClientResponse updateClient(
            Long id,
            ClientRequest request) {

        Client client =
                clientRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Client not found with id: " + id
                                )
                        );

        // Check email belongs to another client
        clientRepository.findByEmail(request.getEmail())
                .ifPresent(existingClient -> {

                    if (!existingClient.getId().equals(id)) {
                        throw new RuntimeException(
                                "Email already belongs to another client"
                        );
                    }
                });

        // Check passport belongs to another client
        if (request.getPassportNumber() != null &&
                !request.getPassportNumber().isBlank()) {

            clientRepository
                    .findByPassportNumber(
                            request.getPassportNumber()
                    )
                    .ifPresent(existingClient -> {

                        if (!existingClient.getId().equals(id)) {
                            throw new RuntimeException(
                                    "Passport number already belongs to another client"
                            );
                        }
                    });
        }

        mapRequestToEntity(request, client);

        Client updatedClient =
                clientRepository.save(client);

        return mapEntityToResponse(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {

        Client client =
                clientRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Client not found with id: " + id
                                )
                        );

        clientRepository.delete(client);
    }

    // =========================================================
    // Map Request → Entity
    // =========================================================

    private void mapRequestToEntity(
            ClientRequest request,
            Client client) {

        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setGender(request.getGender());
        client.setDateOfBirth(request.getDateOfBirth());
        client.setNationality(request.getNationality());
        client.setPhone(request.getPhone());
        client.setEmail(request.getEmail());

        client.setPassportNumber(
                request.getPassportNumber()
        );

        client.setPassportIssueDate(
                request.getPassportIssueDate()
        );

        client.setPassportExpiryDate(
                request.getPassportExpiryDate()
        );

        client.setPassportIssuePlace(
                request.getPassportIssuePlace()
        );

        client.setEmergencyContactName(
                request.getEmergencyContactName()
        );

        client.setEmergencyContactPhone(
                request.getEmergencyContactPhone()
        );

        client.setEmergencyContactRelation(
                request.getEmergencyContactRelation()
        );

        client.setCountry(request.getCountry());
        client.setCity(request.getCity());
        client.setAddress(request.getAddress());
    }

    // =========================================================
    // Map Entity → Response
    // =========================================================

    private ClientResponse mapEntityToResponse(
            Client client) {

        return ClientResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .gender(client.getGender())
                .dateOfBirth(client.getDateOfBirth())
                .nationality(client.getNationality())
                .phone(client.getPhone())
                .email(client.getEmail())
                .passportNumber(client.getPassportNumber())
                .passportIssueDate(
                        client.getPassportIssueDate()
                )
                .passportExpiryDate(
                        client.getPassportExpiryDate()
                )
                .passportIssuePlace(
                        client.getPassportIssuePlace()
                )
                .emergencyContactName(
                        client.getEmergencyContactName()
                )
                .emergencyContactPhone(
                        client.getEmergencyContactPhone()
                )
                .emergencyContactRelation(
                        client.getEmergencyContactRelation()
                )
                .country(client.getCountry())
                .city(client.getCity())
                .address(client.getAddress())
                .active(client.getActive())
                .build();
    }
}