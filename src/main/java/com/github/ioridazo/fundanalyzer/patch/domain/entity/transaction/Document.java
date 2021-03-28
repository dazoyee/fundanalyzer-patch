package com.github.ioridazo.fundanalyzer.patch.domain.entity.transaction;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Document {

    private Integer id;

    private String documentId;

    private String documentTypeCode;

    private String edinetCode;

    private LocalDate documentPeriod;

    private LocalDate submitDate;

    private String downloaded;

    private String decoded;

    private String scrapedNumberOfShares;

    private String numberOfSharesDocumentPath;

    private String scrapedBs;

    private String bsDocumentPath;

    private String scrapedPl;

    private String plDocumentPath;

    private String scrapedCf;

    private String cfDocumentPath;

    private String removed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
