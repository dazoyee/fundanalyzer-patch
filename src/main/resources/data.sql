-- test data
-- update analysis result
insert into industry(name, created_at)
values ('i2', sysdate)
;

insert into company(code, company_name, industry_id, edinet_code, created_at, updated_at)
values ('00000', '', 1, 'ec0000', sysdate, sysdate),
       ('99999', '', 1, 'ec9999', sysdate, sysdate)
;

insert into document
(document_id, edinet_code, document_type_code, document_period, submit_date, created_at, updated_at)
values ('TEST0008', 'ec9999', '120', '2021-03-28', sysdate, sysdate, sysdate),
       ('TEST0009', 'ec0000', '120', '2021-04-03', sysdate, sysdate, sysdate)
;

insert into analysis_result
(company_code, document_period, corporate_value, document_type_code, submit_date, document_id, created_at)
values ('00000', '2021-04-03', 10, '000', sysdate, 'TEST0008', sysdate)
;

INSERT INTO `stock_price`
(`company_code`, `target_date`, `stock_price`, `opening_price`, `high_price`, `low_price`, `volume`, `per`, `pbr`,
 `roe`, `number_of_shares`, `market_capitalization`, `dividend_yield`, `shareholder_benefit`, `source_of`, `created_at`)
VALUES ('00000', '2021-04-03', 2866, 2975, 2989, 2855, 574100, '36.7倍', '13.50倍', '36.80％', '21,418,865株',
        '61,386百万円', NULL, 'ギフト券 その他', '1', '2021-06-19 13:00:02');

INSERT INTO `investment_indicator`
(`stock_id`, `analysis_result_id`, `company_code`, `target_date`, `price_corporate_value_ratio`, `per`, `pbr`,
 `graham_index`, `document_id`, `created_at`)
VALUES (1, 1, '00000', '2021-04-03', 0.309616, NULL, NULL, NULL, 'TEST0008', '2022-12-10 10:50:04');

INSERT INTO `valuation`
(`company_code`, `target_date`, `stock_price`, `goals_stock`, `graham_index`, `day_since_submit_date`,
 `difference_from_submit_date`, `submit_date_ratio`, `discount_value`, `discount_rate`, `submit_date`,
 `corporate_value`, `stock_price_of_submit_date`, `graham_index_of_submit_date`, `document_id`, `created_at`)
VALUES ('00000', '2021-04-03', 1502, NULL, NULL, 0, -111.3, 0.93, 733.69, 1.49, '2021-04-03', 2235.69, 1613.3, NULL,
        'TEST0008', '2022-07-11 00:10:35');
