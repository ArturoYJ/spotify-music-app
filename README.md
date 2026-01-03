# Spotify Music App

[![Angular](https://img.shields.io/badge/Angular-20-DD0031?logo=angular)](https://angular.dev/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7F52FF?logo=kotlin)](https://kotlinlang.org/)
[![Ktor](https://img.shields.io/badge/Ktor-3.0-orange?logo=ktor)](https://ktor.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?logo=postgresql)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

Aplicación full-stack tipo Spotify desarrollada como **proyecto académico** para practicar desarrollo web moderno. Permite explorar y gestionar artistas, álbumes y canciones mediante una interfaz elegante y una API REST robusta.

> **Estado del Proyecto:** En desarrollo activo. El frontend y backend operan de forma independiente y aún no están integrados.

---

## Arquitectura del Proyecto

Este repositorio está organizado como un **monorepo** que contiene todos los componentes de la aplicación:

```
spotify-music-app/
├── frontend/   → SPA en Angular 20 con diseño responsivo
├── backend/    → API REST en Ktor con Arquitectura Hexagonal
└── database/   → Schema SQL para PostgreSQL
```

### Diagrama de Componentes

```
┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐
│    Frontend     │ ────► │     Backend     │ ────► │    Database     │
│   Angular 20    │  API  │   Kotlin/Ktor   │  SQL  │  PostgreSQL 16  │
│   TypeScript    │ REST  │   Hexagonal     │       │                 │
└─────────────────┘       └─────────────────┘       └─────────────────┘
```

---

## Stack Tecnológico

### Frontend
| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| Angular | 20.3.4 | Framework web SPA |
| TypeScript | - | Lenguaje principal |
| SCSS | - | Estilos y temas |
| OAuth 2.0 | - | Autenticación con Spotify API |

### Backend
| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| Kotlin | 2.0 | Lenguaje de programación |
| Ktor | 3.0 | Framework web asíncrono |
| Exposed | - | ORM de JetBrains |
| PostgreSQL | 16 | Base de datos relacional |

---

## Modelo de Datos

La base de datos sigue un esquema relacional con tres entidades principales:

```
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│  ARTISTAS   │ 1───N │   ALBUMES   │ 1───N │   TRACKS    │
├─────────────┤       ├─────────────┤       ├─────────────┤
│ id (UUID)   │       │ id (UUID)   │       │ id (UUID)   │
│ name        │       │ title       │       │ title       │
│ genre       │       │ release_year│       │ duration    │
│ created_at  │       │ artist_id   │◄──────│ album_id    │◄──
│ updated_at  │       │ created_at  │       │ created_at  │
└─────────────┘       │ updated_at  │       │ updated_at  │
                      └─────────────┘       └─────────────┘
```

---

## Instalación y Ejecución

### Requisitos Previos

- [Node.js](https://nodejs.org/) v18+
- [Angular CLI](https://angular.dev/tools/cli) v20+
- [JDK](https://adoptium.net/) 17+
- [PostgreSQL](https://www.postgresql.org/) 16+
- Credenciales de [Spotify Developer](https://developer.spotify.com/dashboard)

### 1 Clonar el Repositorio

```bash
git clone https://github.com/ArturoYJ/spotify-music-app.git
cd spotify-music-app
```

### 2 Configurar la Base de Datos

```bash
# Conectarse a PostgreSQL y ejecutar el script
psql -U tu_usuario -d tu_base_de_datos -f database/baseSpotifyMusicClon.sql
```

### 3 Ejecutar el Backend

```bash
cd backend

# Configurar variables de entorno (ver backend/README.md)
./gradlew run
```
El servidor iniciará en `http://localhost:3000`

### 4 Ejecutar el Frontend

```bash
cd frontend
npm install
ng serve
```

---

## Documentación por Componente

Cada componente tiene su propio README con instrucciones detalladas:

| Componente | Documentación |
|------------|---------------|
| Frontend | [frontend/README.md](./frontend/README.md) |
| Backend | [backend/README.md](./backend/README.md) |

---

## Objetivos de Aprendizaje

Este proyecto académico tiene como objetivo desarrollar habilidades en:

- Consumo e integración de APIs REST
- Autenticación OAuth 2.0 (Spotify Web API)
- Arquitectura Hexagonal (Ports & Adapters)
- Desarrollo de SPAs con Angular moderno
- Diseño de bases de datos relacionales
- Buenas prácticas en desarrollo full-stack

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

<p align="center">
  Desarrollado con ❤️ por <a href="https://github.com/ArturoYJ">ArturoYJ</a>
</p>
