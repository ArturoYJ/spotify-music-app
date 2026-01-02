# Spotify Music Ktor API

![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7F52FF?logo=kotlin)
![Ktor](https://img.shields.io/badge/Ktor-3.0-orange?logo=ktor)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?logo=postgresql)
![License](https://img.shields.io/badge/license-MIT-green)

Backend ligero y asíncrono diseñado para una app web de música. Este proyecto demuestra la implementación de **Arquitectura Hexagonal (Ports & Adapters)** utilizando el framework Ktor.

> **Frontend:** Consulta el [cliente Angular](https://github.com/ArturoYJ/spotify-music-player-angular.git) en mi perfil de GitHub (tener en cuenta que es un proyecto en desarrollo).

## Filosofía del Proyecto

El objetivo principal no es solo crear una API, sino desacoplar la lógica de negocio de los detalles de implementación:
* **Domain:** Contiene los modelos (`Artist`, `Album`) y puertos (`MusicRepository`) agnósticos a la base de datos.
* **Infrastructure:** Implementa la persistencia usando **Exposed** (ORM) y expone la API REST.

## Estructura del Proyecto

```
📁 src/main/kotlin
├── domain/          # Modelos y puertos (lógica de negocio pura)
├── infrastructure/  # Adaptadores (Exposed, controladores REST)
└── application/     # Casos de uso
```

## Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| Kotlin | 2.0 | Lenguaje de programación |
| Ktor | 3.0 | Framework web asíncrono |
| PostgreSQL | 16 | Base de datos relacional |
| Exposed | - | ORM de JetBrains |

## Instalación y Ejecución

### Variables de Entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `DB_HOST` | Host de PostgreSQL | `tuHost` |
| `DB_PORT` | Puerto de PostgreSQL | `tuPuerto` |
| `DB_NAME` | Nombre de la base de datos | `tuBaseDeDatos` |
| `DB_USER` | Usuario de PostgreSQL | `tuUsuario` |
| `DB_PASSWORD` | Contraseña de PostgreSQL | `tuContraseña` |

### Pasos

1. **Clonar el repositorio:**
    ```bash
    git clone https://github.com/ArturoYJ/spotify-music-ktor-api.git
    cd spotify-music-ktor-api
    ```

2. **Configurar Base de Datos:**
    Asegúrate de que tus credenciales en `src/main/resources/application.yaml` coincidan con tu instancia local de Postgres.

3. **Ejecutar el servidor:**
    ```bash
    ./gradlew run
    ```
    El servidor iniciará en `http://0.0.0.0:3000`.

## API Endpoints

La API expone recursos RESTful para la gestión de metadatos musicales:

| Método | Endpoint         | Descripción                          |
|--------|------------------|--------------------------------------|
| GET    | `/api/artistas`  | Obtener todos los artistas           |
| POST   | `/api/artistas`  | Registrar un nuevo artista           |
| GET    | `/api/albumes`   | Obtener álbumes                      |
| POST   | `/api/albumes`   | Crear álbum (Vinculado a ArtistID)   |
| GET    | `/api/tracks`    | Obtener canciones                    |
| POST   | `/api/tracks`    | Subir canción (Vinculada a AlbumID)  |

### Ejemplos de Request

**POST `/api/artistas`**
```json
{
  "nombre": "Bad Bunny",
  "genero": "Reggaeton"
}
```

**POST `/api/albumes`**
```json
{
  "nombre": "Un Verano Sin Ti",
  "artistaId": 1
}
```

**POST `/api/tracks`**
```json
{
  "nombre": "Me Porto Bonito",
  "albumId": 1
}
```

> **Nota:** Incluye protección de integridad referencial a nivel de aplicación (no permite borrar artistas si tienen álbumes asociados).

## Testing

El proyecto incluye una colección de Postman (`test_evaluation_backend.json`) para pruebas de integración automatizadas.

```bash
# Ejecutar tests unitarios
./gradlew test
```

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

<p align="center">
  Desarrollado con ❤️ por <a href="https://github.com/ArturoYJ">ArturoYJ</a>
</p>