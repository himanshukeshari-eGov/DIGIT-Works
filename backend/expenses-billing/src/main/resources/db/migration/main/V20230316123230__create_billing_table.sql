CREATE TABLE eg_wms_bill_demand (
  id                            character varying(256),
  tenant_id                     character varying(64) NOT NULL,
  bill_number                   character varying(64),
  bill_date                     bigint NOT NULL,
  net_amount                    double
  gross_amount                  double
  head_of_account               character varying(64),
  ifms_sanction_number          character varying(64),
  purpose                       character varying(256),
  created_by                    character varying(256)  NOT NULL,
  last_modified_by              character varying(256),
  created_time                  bigint,
  last_modified_time            bigint,
  CONSTRAINT uk_eg_wms_bill_demand UNIQUE (bill_number),
  CONSTRAINT pk_eg_wms_bill_demand PRIMARY KEY (id)
);

CREATE TABLE eg_wms_demand_beneficiaries (
   id                           character varying(256),
   bill_number                  character varying(64),
   name                         character varying(64),
   account_number               character varying(64),
   ifsc_code                    character varying(64),
   mobile_number                character varying(64),
   address                      character varying(64),
   account_type                 character varying(64),
   amount                       double,
   purpose                      character varying(64),
   status                       character varying(64),
   created_by                   character varying(256)  NOT NULL,
   last_modified_by             character varying(256),
   created_time                 bigint,
   last_modified_time           bigint,
   CONSTRAINT pk_eg_wms_demand_beneficiaries PRIMARY KEY (id),
   CONSTRAINT fk_eg_wms_demand_beneficiaries FOREIGN KEY (bill_number) REFERENCES eg_wms_bill_demand (bill_number)
);

CREATE TABLE eg_wms_bill_payment (
  bill_number                 character varying(64),
  bill_date                   bigint NOT NULL,
  voucher_number              character varying(64),
  voucher_date                character varying(64)
);

CREATE TABLE eg_wms_beneficiary_transfer_status (
  bill_number                character varying(64),
  account_number             character varying(64),
  ifsc_code                  character varying(64),
  rbi_sequence_number        character varying(64),
  sequence_date              character varying(64),
  end_to_end_id              character varying(64),
  status                     character varying(64)
);

CREATE INDEX IF NOT EXISTS index_eg_wms_bill_demand_id ON eg_wms_bill_demand (id);
CREATE INDEX IF NOT EXISTS index_eg_wms_bill_demand_tenantId ON eg_wms_bill_demand (tenant_id);
CREATE INDEX IF NOT EXISTS index_eg_wms_bill_demand_bill_number ON eg_wms_bill_demand (bill_number);
CREATE INDEX IF NOT EXISTS index_eg_wms_bill_demand_bill_date ON eg_wms_bill_demand (bill_date);