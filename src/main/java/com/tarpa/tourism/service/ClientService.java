package com.tarpa.tourism.service;

import com.tarpa.tourism.request.ClientRequest;
import com.tarpa.tourism.response.ClientResponse;

import java.util.List;

public interface ClientService {

    ClientResponse createClient(ClientRequest request);

    ClientResponse getClientById(Long id);

    List<ClientResponse> getAllClients();

    ClientResponse updateClient(Long id, ClientRequest request);

    void deleteClient(Long id);
}