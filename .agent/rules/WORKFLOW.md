---
trigger: always_on
---

Step,Action,Repository Target
1. Define,Draft/Update an ADR or OpenAPI spec.,/docs or /api-contracts
2. Provision,"Update Terraform for IRSA, SQS, or Secrets.",/infrastructure or local /terraform
3. Implement,Write TDD-backed code (Spring Boot or TS).,/services or /lambdas
4. Configure,Set up feature flags or dynamic config.,/configuration/appconfig
5. Integrate,Add the new build/deploy job to CI.,.gitlab-ci.yml