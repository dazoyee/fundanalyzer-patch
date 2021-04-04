-- test data
-- update analysis result
insert into industry(name, created_at)
values ('i2', sysdate)
;

insert into company(code, company_name, industry_id, edinet_code, created_at, updated_at)
values ('00000', '', 1, 'ec0000', sysdate, sysdate),
       ('99999', '', 1, 'ec9999', sysdate, sysdate)
;

insert into document (document_id, edinet_code, document_type_code, document_period, submit_date, created_at,
                      updated_at)
values ('TEST0008', 'ec9999', '120', '2021-03-28', sysdate, sysdate, sysdate),
       ('TEST0009', 'ec0000', '120', '2021-04-03', sysdate, sysdate, sysdate)
;

insert into analysis_result (company_code, document_period, corporate_value, document_type_code, submit_date,
                             document_id, created_at)
values ('00000', '2021-04-03', 10, '000', sysdate, 'TEST0008', sysdate)
;

-- update financial statement
insert into industry(name, created_at)
values ('i1', sysdate)
;

insert into company(company_name, industry_id, edinet_code, created_at, updated_at)
values ('', 1, 'ec0001', sysdate, sysdate)
;

insert into document (document_id, document_type_code, document_period, submit_date, created_at, updated_at)
values ('TEST0006', '120', '2021-03-28', sysdate, sysdate, sysdate),
       ('TEST0007', '120', '2021-03-28', sysdate, sysdate, sysdate)
;

insert into financial_statement (edinet_code, financial_statement_id, subject_id, period_start, period_end,
                                 document_type_code, submit_date, document_id, created_at)
values ('ec0001', '1', '1', '2021-03-28', '2021-03-28', '000', sysdate, 'TEST0006', sysdate)
;

insert into edinet_document (doc_id, edinet_code, doc_type_code, period_start, period_end, submit_date_time,
                             parent_doc_id, created_at)
values ('TEST0007', 'ec0001', 120, '2021-03-28', '2021-03-28', '2021-04-03 09:00', NULL, sysdate)
;

-- document period
insert into edinet_document (doc_id, doc_type_code, period_end, parent_doc_id, created_at)
values ('TEST0001', '120', '2021-03-28', NULL, sysdate),
       ('TEST0002', '130', NULL, 'TEST0001', sysdate),
       ('TEST0003', '120', '2021-03-28', NULL, sysdate),
       ('TEST0004', '130', NULL, 'TEST0003', sysdate),
       ('TEST0005', '130', NULL, 'TEST0003', sysdate)
;

insert into document (document_id, edinet_code, document_type_code, document_period, submit_date, created_at,
                      updated_at)
values ('TEST0001', 'ec0000', '120', '2021-03-28', sysdate, sysdate, sysdate),
       ('TEST0002', 'ec0000', '130', NULL, sysdate, sysdate, sysdate),
       ('TEST0003', 'ec0000', '120', '2021-03-28', sysdate, sysdate, sysdate),
       ('TEST0004', 'ec0000', '130', NULL, sysdate, sysdate, sysdate),
       ('TEST0005', 'ec0000', '130', NULL, sysdate, sysdate, sysdate)
;

-- scraping_keyword
insert
into scraping_keyword (financial_statement_id, keyword, remarks)
values ('1', 'jpcrp_cor:ConsolidatedBalanceSheetTextBlock', '連結貸借対照表'),
       ('1', 'jpcrp_cor:BalanceSheetTextBlock', '貸借対照表'),
       ('2', 'jpcrp_cor:ConsolidatedStatementOfIncomeTextBlock', '連結損益計算書'),
       ('2', 'jpcrp_cor:StatementOfIncomeTextBlock', '損益計算書'),
       ('4', 'jpcrp_cor:IssuedSharesTotalNumberOfSharesEtcTextBlock', '株式総数')
;

-- bs_subject
insert into bs_subject (outline_subject_id, detail_subject_id, name)
values ('1', '3', '流動資産'),
       ('1', '1', '流動資産合計'),
       ('2', null, '有形固定資産'),
       ('3', null, '無形固定資産'),
       ('4', '2', '投資その他の資産'),
       ('4', '1', '投資その他の資産合計'),
       ('5', '1', '固定資産合計'),
       ('6', null, '繰延資産'),
       ('7', null, '資産合計'),
       ('8', '3', '流動負債'),
       ('8', '1', '流動負債合計'),
       ('9', '3', '固定負債'),
       ('9', '1', '固定負債合計'),
       ('10', null, '負債合計'),
       ('11', null, '投資主資本'),
       ('12', null, '剰余金'),
       ('13', null, '投資主合計'),
       ('14', null, '純資産合計'),
       ('15', null, '負債純資産合計'),
       ('1', '2', '流動資産計'),
       ('5', '2', '固定資産計'),
       ('8', '2', '流動負債計'),
       ('9', '2', '固定負債計')
--  (null, '1', '現金及び預金'), (null, '1', '信託現金及び信託預金'), (null, '1', '営業未収入金'), (null, '1', 'リース投資資産'), (null, '1', '前払費用'),
--  (null, '1', 'その他'), (null, '1', '貸倒引当金'),
--  (null, '2', '建物'), (null, '2', '減価償却累計額'), (null, '2', '建物（純額）'), (null, '2', '建物附属設備'), (null, '2', '減価償却累計額'),
--  (null, '2', '建物附属設備（純額）'), (null, '2', '構築物'), (null, '2', '減価償却累計額'), (null, '2', '構築物（純額）'), (null, '2', '機械及び装置'),
--  (null, '2', '減価償却累計額'), (null, '2', '機械及び装置（純額）'), (null, '2', '工具、器具及び備品'), (null, '2', '減価償却累計額'), (null, '2', '工具、器具及び備品（純額）'),
--  (null, '2', '土地'), (null, '2', '建設仮勘定'), (null, '2', '信託建物'), (null, '2', '減価償却累計額'), (null, '2', '信託建物（純額）'),
--  (null, '2', '信託建物附属設備'), (null, '2', '減価償却累計額'), (null, '2', '信託建物附属設備（純額）'), (null, '2', '信託構築物'), (null, '2', '減価償却累計額'),
--  (null, '2', '信託構築物（純額）'), (null, '2', '信託機械及び装置'), (null, '2', '減価償却累計額'), (null, '2', '信託機械及び装置（純額）'), (null, '2', '信託工具、器具及び備品'),
--  (null, '2', '減価償却累計額'), (null, '2', '信託工具、器具及び備品（純額）'), (null, '2', '信託土地'), (null, '2', '信託建設仮勘定'), (null, '2', '有形固定資産合計'),
--  (null, '3', '借地権'), (null, '3', '信託借地権'), (null, '3', 'その他'), (null, '3', '無形固定資産合計'),
--  (null, '4', '修繕積立金'), (null, '4', '敷金及び保証金'), (null, '4', '信託差入敷金及び保証金'), (null, '4', '長期前払費用'),
--  (null, '6', '投資法人債発行費'), (null, '6', '繰延資産合計'),
--  (null, '8', '営業未払金'), (null, '8', '短期借入金'), (null, '8', '8年内返済予定の長期借入金'), (null, '8', '8年内償還予定の投資法人債'), (null, '8', '未払金'),
--  (null, '8', '未払費用'), (null, '8', '未払法人税等'), (null, '8', '未払消費税等'), (null, '8', '前受金'), (null, '8', 'その他'),
--  (null, '9', '投資法人債'), (null, '9', '長期借入金'), (null, '9', '預り敷金及び保証金'), (null, '9', '資産除去債務'),
--  (null, '11', '出資総額'),
--  (null, '12', '圧縮積立金'), (null, '12', '任意積立金合計'), (null, '12', '当期未処分利益又は当期未処理損失（△）'), (null, '12', '剰余金合計'),
--  (null, '13', '投資主資本合計'),
-- TODO 項目追加
;

-- TODO 追加したらEnumの修正
-- pl_subject
insert into pl_subject (outline_subject_id, detail_subject_id, name)
values ('1', null, '営業収益'),
--  (null, '1', '賃貸事業収入'), (null, '1', 'その他賃貸事業収入'), (null, '1', '営業収益合計'),
       ('2', null, '営業費用'),
--  (null, '2', '賃貸事業費用'), (null, '2', '不動産等売却損'), (null, '2', '資産運用報酬'), (null, '2', '資産保管及び一般事務委託手数料'), (null, '2', '役員報酬'),
--  (null, '2', '会計監査人報酬'), (null, '2', 'その他営業費用'), (null, '2', '営業費用合計'),
       ('3', '1', '営業利益'),
       ('3', '2', '営業利益又は営業損失（△）'),
       ('3', '3', '営業利益又は営業損失(△)'),
       ('3', '4', '営業損失（△）'),
       ('3', '5', '営業損失(△)'),
       ('3', '6', '営業利益（△は損失）'),
       ('4', null, '営業外収益'),
--  (null, '4', '受取利息'), (null, '4', '未払分配金戻入'), (null, '4', '還付加算金'), (null, '4', 'その他'), (null, '4', '営業外収益合計'),
       ('5', null, '営業外費用'),
--  (null, '5', '支払利息'), (null, '5', '投資法人債利息'), (null, '5', '投資法人債発行費償却'), (null, '5', '融資手数料'), (null, '5', 'その他'),
--  (null, '5', '営業外費用合計'),
       ('6', null, '経常利益'),
       ('7', null, '特別損失'),
--  (null, '7', '災害による損失'), (null, '7', '特別損失合計'),
       ('8', null, '税引前当期純利益'),
       ('9', null, '法人税、住民税及び事業税'),
       ('10', null, '法人税等合計'),
       ('11', null, '当期純利益'),
       ('12', null, '前期繰越利益'),
       ('13', null, '当期未処分利益又は当期未処理損失（△）')
;
