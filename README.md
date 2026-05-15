# GREENHERB API

API REST desarrollada para la plataforma GREENHERB, orientada a la gestión inteligente de una estufa de hierbas aromáticas.

El proyecto incluye autenticación JWT, gestión de planes de cultivo, registro de mediciones ambientales, generación de alertas y una batería completa de tests unitarios, de integración y de sistema.

---

# Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.x
- Maven
- Spring Web
- Spring Security
- Spring Data JPA
- JWT Authentication
- H2 Database
- Swagger / OpenAPI
- Lombok
- JUnit 5
- Mockito
- MockMvc
- JaCoCo
- Postman
- Newman

---

# Requisitos previos

Antes de ejecutar el proyecto es necesario tener instalado:

- Java 17 o superior
- IntelliJ IDEA Community
- Git
- Maven o Maven Wrapper
- Postman
- Node.js y npm (solo para Newman)

Comprobar Java:

```bash
java -version

Comprobar Git:

git --version

Comprobar Node.js:

node -v
npm -v
Clonar el repositorio
git clone https://github.com/Diego-Alonso-05/greenherb-api.git
cd greenherb-api
Abrir el proyecto
Abrir IntelliJ IDEA.
Seleccionar File > Open.
Abrir la carpeta greenherb-api.
Esperar a que Maven sincronice las dependencias.
Activar Lombok si fuese necesario:
File > Settings > Plugins > Lombok
File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors
Activar Enable annotation processing.
Ejecutar la aplicación

Ejecutar la clase principal:

GreenherbApiApplication

La aplicación se ejecutará en:

http://localhost:8080

Swagger estará disponible en:

http://localhost:8080/swagger-ui/index.html
Usuario administrador

El sistema crea automáticamente un usuario administrador mediante DataLoader.

Credenciales:

Username: admin
Password: 1234
Autenticación JWT

Para acceder a endpoints protegidos:

Hacer login:
POST /auth/login

Body:

{
  "username": "admin",
  "password": "1234"
}
Copiar el token JWT devuelto.
Usar:
Authorization: Bearer TOKEN

en Swagger o Postman.

Endpoints principales
Método	Endpoint	Descripción
POST	/auth/login	Login y generación de JWT
GET	/plans	Obtener planes
POST	/plans	Crear plan
GET	/plans/{id}	Obtener plan por ID
PUT	/plans/{id}	Actualizar plan
DELETE	/plans/{id}	Eliminar plan
POST	/measurements	Registrar medición
GET	/alerts	Obtener alertas
PATCH	/alerts/{id}/resolve	Resolver alerta
PATCH	/alerts/{id}/ignore	Ignorar alerta con justificación
Ejecutar tests
Desde IntelliJ
Abrir pestaña Maven.
Ir a Lifecycle.
Ejecutar:
test

Debe aparecer:

BUILD SUCCESS
Desde terminal

Si Maven está instalado:

mvn clean test

Si no:

./mvnw clean test

En Windows:

.\mvnw.cmd clean test
Tests unitarios

Ubicación:

src/test/java/com/greenherb/greenherb_api/service/

Tests implementados:

AuthServiceTest
PlanServiceTest
MeasurementServiceTest
AlertServiceTest

Se utilizaron:

JUnit 5
Mockito
@Mock
@InjectMocks

Los tests validan:

autenticación,
validación de planes,
generación de alertas,
resolución e ignorado de alertas,
manejo de excepciones,
edge cases.
Tests de integración

Ubicación:

src/test/java/com/greenherb/greenherb_api/integration/

Tests implementados:

AuthControllerIntegrationTest
PlanControllerIntegrationTest
MeasurementControllerIntegrationTest
AlertControllerIntegrationTest

Se utilizaron:

SpringBootTest
AutoConfigureMockMvc
MockMvc
H2 Database
JWT real

Estos tests validan:

endpoints completos,
respuestas HTTP,
autenticación JWT,
persistencia,
integración entre controladores, servicios y repositorios.
Tests de sistema

Los tests de sistema fueron implementados usando Postman.

Colección exportada:

postman/GREENHERB-System-Tests.postman_collection.json

Flujo probado:

Login.
Generación de JWT.
Creación de plan activo.
Consulta de planes.
Registro de medición crítica.
Consulta de alertas.
Resolución de alerta.

La colección utiliza una variable de entorno:

{{token}}

El token se guarda automáticamente tras el login.

Ejecutar Newman

Instalar Newman:

npm install -g newman

Ejecutar colección:

newman run postman/GREENHERB-System-Tests.postman_collection.json

Importante:
la API debe estar ejecutándose antes de lanzar Newman.

Cobertura con JaCoCo

JaCoCo está configurado en:

pom.xml

Para generar el informe:

mvn clean test

El reporte se genera en:

target/site/jacoco/index.html

Resultados obtenidos:

Métrica	Resultado
Instruction Coverage	82%
Branch Coverage	70%

Captura guardada en:

docs/evidence/jacoco-coverage.png
Técnicas formales aplicadas
Particionamiento de equivalencia

Archivo:

docs/equivalence-partitioning.md

Aplicado a:

temperatura,
humedad,
validación de planes,
justificaciones de alertas.
Análisis de valores límite

Archivo:

docs/boundary-value-analysis.md

Valores probados:

Parámetro	min-1	min	nominal	max	max+1
Temperatura	17	18	23	28	29
Humedad	39	40	60	80	81
MC/DC y cobertura de condiciones múltiples

Archivo:

docs/mcdc.md

Aplicado a:

alertas críticas,
alertas warning,
validación de planes,
validación de ignorar alertas.
Matriz de trazabilidad

Archivo:

docs/traceability-matrix.md

Relaciona:

requisitos,
endpoints,
casos de test,
técnicas aplicadas,
resultados esperados.
Defectos encontrados

Archivo:

docs/defects.md

Defectos principales detectados:

Múltiples planes activos generan conflicto.
Mediciones sin plan activo.
Ignorar alerta sin justificación.
Evidencias

Ubicación:

docs/evidence/

Incluye:

ejecución correcta de Postman,
cobertura JaCoCo,
capturas del sistema.
Problemas conocidos
Múltiples planes activos

Si existen varios planes activos:

Query did not return a unique result
Sin plan activo

Si se registra una medición sin plan activo:

No active plan found
Ignorar alerta sin justificación

La API devuelve:

HTTP 400 Bad Request
Estructura del proyecto
greenherb-api
│
├── src
│   ├── main
│   └── test
│
├── docs
│   ├── evidence
│   ├── traceability-matrix.md
│   ├── equivalence-partitioning.md
│   ├── boundary-value-analysis.md
│   ├── mcdc.md
│   └── defects.md
│
├── postman
│   └── GREENHERB-System-Tests.postman_collection.json
│
└── pom.xml
GitHub

Repositorio:

https://github.com/Diego-Alonso-05/greenherb-api

Notas finales

La versión actual del backend implementa y prueba los siguientes módulos:

autenticación,
planes,
mediciones,
alertas.

Otros módulos mencionados en el enunciado original como:

herbs,
batches,
tasks,
automation,
reports,
audit,

quedan fuera del alcance actual del proyecto.

El proyecto incluye:

tests unitarios,
tests de integración,
tests de sistema,
JWT testing,
MockMvc,
Mockito,
JaCoCo,
Postman,
Newman,
técnicas formales de testing,
matriz de trazabilidad,
defect report,
evidencias de ejecución.
