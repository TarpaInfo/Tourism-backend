package com.tarpa.tourism.document.repository;

import com.tarpa.tourism.document.entity.ClientDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientDocumentRepository extends JpaRepository<ClientDocument, Long> {

    List<ClientDocument> findByBookingId(Long bookingId);

    List<ClientDocument> findByClientId(Long clientId);

    Optional<ClientDocument> findByStoredFileName(String storedFileName);
}