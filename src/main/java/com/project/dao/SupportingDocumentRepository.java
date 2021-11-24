package com.project.dao;

import com.project.domain.Application;
import com.project.domain.SupportingDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportingDocumentRepository extends JpaRepository<SupportingDocument, String> {

    List<SupportingDocument> findAllByApplication(Application application);
}
