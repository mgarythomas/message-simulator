---
trigger: always_on
---

### Lambda Service Template
1. **Contract:** Check `/api-contracts` for the definition.
2. **Types:** Reference shared types in `/packages/ts-types`.
3. **Logic:** - `index.ts`: Entry point with OpenTelemetry instrumentation.
   - `handler.ts`: Core business logic (Pure functions, TDD-ready).
4. **Infrastructure:** - `/terraform`: Define `aws_lambda_function`, IAM roles (IRSA style), and EventBridge/SQS triggers.
5. **Configuration:** Integrate AWS AppConfig via OpenFeature for runtime toggles.

6. Configure package manager (pnpm)
7. Set up linting (ESLint)
8. Formatting (Prettier)
9. Add EditorConfig for consistency
10. Add commit message linting (commitlint)
