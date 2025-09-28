# vue_dashboard_back

Backend for the Vue Dashboard project — Spring Boot REST API to serve product data to the front-end.

> Lightweight Spring Boot service (Maven) providing product endpoints used by the Vue dashboard frontend.

---

## Table of Contents
- [Features](#features)
- [Tech stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Quick start (local)](#quick-start-local)
- [Environment variables](#environment-variables)
- [Run with Docker Compose (recommended for dev)](#run-with-docker-compose-recommended-for-dev)
- [Build & Run (production)](#build--run-production)
- [API examples](#api-examples)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## Features
- REST endpoints for products (list, get by id, create/update/delete).
- Pagination and basic filtering (inferred).
- CORS enabled for the frontend (recommended).
- Configurable via environment variables.
- Ready to be extended (security, audits, etc).

> Note: This README assumes a typical Spring Boot + Maven structure. If your `pom.xml` contains different plugins or profiles, paste it here and I’ll adapt the commands and instructions precisely.

---

## Tech stack
- Java 11+ (Java 17 recommended)
- Spring Boot (Maven)
- Spring Data JPA (assumed)
- PostgreSQL (recommended for production)
- (Optional) Spring Boot Actuator for health checks
- (Optional) Flyway / Liquibase for DB migrations

---

## Prerequisites
- Java 11 or newer installed
- Maven (or use the included Maven wrapper `./mvnw`)
- Docker & Docker Compose (optional but recommended for local DB)

---

## Quick start (local)

1. Clone repository
```bash
git clone https://github.com/cleytonorocha/vue_dashboard_back.git
cd vue_dashboard_back
