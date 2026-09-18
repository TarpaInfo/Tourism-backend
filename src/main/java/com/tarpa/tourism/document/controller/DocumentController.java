package com.tarpa.tourism.document.controller;

import com.tarpa.tourism.document.entity.ClientDocument;
import com.tarpa.tourism.document.model.DocumentType;
import com.tarpa.tourism.document.repository.ClientDocumentRepository;
import com.tarpa.tourism.document.service.FileStorageService;
import com.tarpa.tourism.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final FileStorageService fileStorageService;
    private final ClientDocumentRepository documentRepository;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ClientDocument> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("bookingId") Long bookingId,
            @RequestParam("clientId") Long clientId,
            @RequestParam("documentType") DocumentType documentType
    ) {
        String storedFileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/documents/download/")
                .path(storedFileName)
                .toUriString();

        ClientDocument document = ClientDocument.builder()
                .bookingId(bookingId)
                .clientId(clientId)
                .documentType(documentType)
                .originalFileName(file.getOriginalFilename())
                .storedFileName(storedFileName)
                .fileDownloadUri(fileDownloadUri)
                .contentType(file.getContentType())
                .sizeInBytes(file.getSize())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(documentRepository.save(document));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<ClientDocument>> getDocumentsByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(documentRepository.findByBookingId(bookingId));
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // fallback
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}