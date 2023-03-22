// config.js
// const env = process.env.NODE_ENV; // 'dev' or 'test'

var HOST = process.env.EGOV_HOST || "https://works-dev.digit.org/";

if (!HOST) {
  console.log("You need to set the HOST variable");
  process.exit(1);
}

module.exports = {
  auth_token: process.env.AUTH_TOKEN,
  KAFKA_BROKER_HOST:
    process.env.KAFKA_BROKER_HOST || "kafka-v2.kafka-cluster:9092",
  KAFKA_RECEIVE_CREATE_JOB_TOPIC:
    process.env.KAFKA_RECEIVE_CREATE_JOB_TOPIC || "PDF_GEN_RECEIVE",
  KAFKA_BULK_PDF_TOPIC: process.env.KAFKA_BULK_PDF_TOPIC || "BULK_PDF_GEN",
  PDF_BATCH_SIZE: process.env.PDF_BATCH_SIZE || 40,
  DB_USER: process.env.DB_USER || "postgres",
  DB_PASSWORD: process.env.DB_PASSWORD || "postgres",
  DB_HOST: process.env.DB_HOST || "localhost",
  DB_NAME: process.env.DB_NAME || "postgres",
  DB_PORT: process.env.DB_PORT || 5432,
  app: {
    port: parseInt(process.env.APP_PORT) || 8080,
    host: HOST,
    contextPath: process.env.CONTEXT_PATH || "/egov-bff-service",
  },
  configs:{
    DATA_CONFIG_URLS:  'file:///Users/klrao/Documents/pdf-config/data-config/consolidatedreceipt.json',
    FORMAT_CONFIG_URLS: process.env.FORMAT_CONFIG_URLS
  },
  host: {
    mdms: process.env.EGOV_MDMS_HOST || HOST,
    pdf: process.env.EGOV_PDF_HOST || HOST,
    user: process.env.EGOV_USER_HOST || HOST,
    workflow: process.env.EGOV_WORKFLOW_HOST || HOST,
    mdms: process.env.EGOV_MDMS_HOST || HOST || "http://localhost:8085/",
    pdf: process.env.EGOV_PDF_HOST || HOST || "http://localhost:8087/",
    user: process.env.EGOV_USER_HOST || HOST || "http://localhost:8089/",
    workflow:
      process.env.EGOV_WORKFLOW_HOST || HOST || "http://localhost:8091/",
    muster: process.env.WORKS_MUSTER_HOST  || "http://localhost:8070/",
    individual: process.env.WORKS_INDIVIDUAL_HOST  || "http://localhost:8071/",
  },
  paths: {
    pdf_create: "/pdf-service/v1/_createnosave",
    user_search: "/user/_search",
    mdms_search: "/egov-mdms-service/v1/_search",
    workflow_search: "/egov-workflow-v2/egov-wf/process/_search",
    ind_search: "/individual/v1/_search",
    mus_search: "/muster-roll/v1/_search",
  },
};