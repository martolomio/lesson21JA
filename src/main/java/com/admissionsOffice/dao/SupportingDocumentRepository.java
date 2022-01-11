package com.admissionsOffice.dao;

import com.admissionsOffice.domain.Application;
import com.admissionsOffice.domain.SupportingDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportingDocumentRepository extends JpaRepository<SupportingDocument, String> {

    List<SupportingDocument> findAllByApplication(Application application);
}
