ALTER TABLE eg_wms_demand_beneficiaries ADD COLUMN pk_id character varying(256);

ALTER TABLE eg_wms_demand_beneficiaries DROP CONSTRAINT pk_eg_wms_demand_beneficiaries;

ALTER TABLE eg_wms_demand_beneficiaries ADD CONSTRAINT pk_eg_wms_demand_beneficiaries PRIMARY KEY (pk_id);