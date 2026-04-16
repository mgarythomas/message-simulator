---
trigger: always_on
---

The repository structure for all services adheres to the following:


/exchange-platform
│
├── /docs                      # Architecture Decision Records (ADRs) and service docs
│
├── /api-contracts             # OpenAPI Specifications (Single source of truth)
│   ├── /dmz                   # External-facing endpoints (routes to DMZ API Gateway)
│   └── /internal              # Internal endpoints (routes to Internal API Gateway)
│
├── /configuration             # Dynamic configurations & Feature Flags
│   └── /appconfig             # AWS AppConfig deployment definitions (OpenFeature targets)
│
├── /database                  # SQL Schemas and Migrations (managed via Flyway)
│   ├── /migrations            # Versioned Flyway SQL scripts (V1__init.sql, V2__add_table.sql)
│   └── /seed-data             # Reference data meant for non-prod environments
│
├── /infrastructure            # Terraform state for platform-specific resources
│   ├── /api-gateway           # TF configuring API endpoints, pointing to CIAM authorizers
│   ├── /ingress               # ALB Ingress routing configurations for EKS services
│   ├── /messaging             # SQS Queues, DLQs, and EventBridge Rules
│   ├── /observability         # OpenTelemetry collectors, CloudWatch logging configs, Elastic SIEM hooks
│   ├── /storage               # S3 bucket policies/configurations required by the platform
│   └── /secrets               # Hashicorp Vault dynamic/static secret configurations
│
├── /services                  # Containerized Applications (Destined for EKS)
│   ├── /[service name]        # Spring Boot Application
│   │   ├── build.gradle.kts   
│   │   ├── Dockerfile         # Multi-stage build for EKS container registry
│   │   ├── /helm              # Helm charts defining Kubernetes Deployment, Service, HPA, ConfigMaps
│   │   ├── /src               # Java/Kotlin code
│   │   └── /terraform         # (Optional) specific TF for the EKS IAM Roles for Service Accounts (IRSA)
│   |   ├── /e2e                     # Playwright API tests for this service
│   |   │   ├── playwright.config.ts
│   |   │   └── api-validation.spec.ts
│   |   └── ...
│   │
│   └── /settlement-service    # Another Spring Boot / Node.js service
│       ├── /helm              # Helm charts specific to the settlement service
│       └── ...
│
├── /lambdas                   # Serverless TypeScript Functions
│   ├── /[lambda name]         # Domain boundary Lambda group
│   │   ├── package.json
│   │   ├── tsconfig.json
│   │   ├── index.ts
│   │   └── /terraform         # Local TF to deploy the Lambda and its localized IAM roles
│   |   ├── index.ts
│   |   ├── /e2e                     # Playwright API tests for this Lambda
│   |   │   ├── playwright.config.ts
│   |   │   └── contract-compliance.spec.ts
│   |   └── ...
│   │
│   └── /notification-handler  # Async EventBridge/SQS driven Lambda
│       └── ...
│
├── /packages                  # Shared Libraries (Internal Monorepo Packages)
│   ├── /ts-types              # Shared TypeScript interfaces (e.g., generated from OpenAPI)
│   └── /java-common           # Shared Java logic (e.g., CIAM validation wrappers, error handling)
│
├── .gitlab-ci.yml             # GitLab CI/CD Pipeline Definitions
├── Makefile                   # Developer CLI wrapping complex build/deploy commands
└── package.json               # Root monorepo tooling (Turborepo)

