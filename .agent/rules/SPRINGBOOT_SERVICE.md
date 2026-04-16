---
trigger: always_on
---

### Spring Boot Service Template

1. **Contract:** Implement interfaces generated from `/api-contracts` OpenAPI. APIs support Major versions only in path.
2. **Core Logic:**
   - Use `java-common` package for CIAM and standardized error handling.
   - Database access via JPA, ensuring SQL matches `/database/migrations`.
3. **Containerization:** - `Dockerfile`: Use the approved multi-stage build (distroless preferred).
4. **Kubernetes:**
   - `/helm`: Define Deployment (with HPA), Service (ClusterIP), and ServiceAccount (mapping to AWS IRSA).
5. **Resilience:** Implement health checks (`/health/liveness`, `/health/readiness`) for EKS.
6. **Implementation** Spring Boot + Flyway Migration
7. **E2E Testing:**
   - **Directory:** `/e2e`
   - **Tooling:** Playwright.
   - **Focus:** Test the ingress path (ALB -> EKS Service).
   - **Requirement:** Assert that the "Exchange" submission portal logic holds up under cross-service failure scenarios (Circuit Breaker testing).
8. ** Add commit message linting (commitlint)
9. ** Use Spring Boot Version 4.x.x
10. ** Build is to use Gradle 9.4.x



