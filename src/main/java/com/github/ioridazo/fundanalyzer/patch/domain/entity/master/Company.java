package com.github.ioridazo.fundanalyzer.patch.domain.entity.master;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class Company {

    // 証券コード
    private String code;

    // 銘柄名
    private String companyName;

    // 業種
    private Integer industryId;

    // EDINETコード
    private String edinetCode;

    // 上場区分
    private String listCategories;

    // 連結の有無
    private String consolidated;

    // 資本金
    private Integer capitalStock;

    // 決算日
    private String settlementDate;

    // 登録日
    private LocalDateTime createdAt;

    // 更新日
    private LocalDateTime updatedAt;

    public Optional<String> getCode() {
        return Optional.ofNullable(code);
    }
}
