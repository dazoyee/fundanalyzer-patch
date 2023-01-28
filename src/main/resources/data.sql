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
