# WasteManagementSystem
 Technical Test
 Repositorio: 
 El código fuente está disponible en: https://github.com/aaliagab/WasteManagementSystem
 Las configuraciones de los microservicios están en: https://github.com/aaliagab/WasteManagementConfigurations

Pre-requisitos

Instrucciones de Instalación de Microservicios
Pre-requisitos
Antes de comenzar, asegúrate de tener instalado:
Java JDK 17 o superior
Maven para la construcción de proyectos.

WasteManagementSystem
El sistema WasteManagementSystem es una solución modular de microservicios para la gestión de residuos. 
Cada módulo del sistema es un microservicio independiente.

Estructura del Proyecto
El proyecto contiene los siguientes módulos:

CloudConfigServer: Servicio de configuración centralizado.
GatewayService: Puerta de enlace para el enrutamiento y filtrado de solicitudes.
NamingService: Servicio de registro y descubrimiento de servicios (Eureka Server).
WasteManagerAddressService: Microservicio para la gestión de direcciones.
WasteManagerService: Microservicio para la gestión de residuos.

Configuración del Servidor Eureka (NamingService)
Para iniciar el Servidor Eureka, sigue estos pasos:
cd NamingService
mvn clean install
java -jar target/naming-service-0.0.1-SNAPSHOT.jar


Configuración del Servidor de Configuración (CloudConfigServer)
El servidor de configuración gestiona las configuraciones externas de los microservicios.
cd CloudConfigServer
mvn clean install
java -jar target/cloud-config-server-0.0.1-SNAPSHOT.jar

Iniciar los Microservicios
Cada microservicio se puede iniciar de la misma manera. Por ejemplo, para WasteManagerService:
cd WasteManagerService
mvn clean install
java -jar target/waste-manager-service-0.0.1-SNAPSHOT.jar

Verificación
Después del despliegue, todos los microservicios deben estar registrados con Eureka y se pueden monitorear a través del dashboard de Eureka.

Troubleshooting
En caso de problemas:

Revisa que todas las instancias se hayan registrado correctamente en Eureka.
Verifica la conectividad entre los microservicios.
Consulta los logs de cada servicio para identificar posibles errores de configuración.



Documentación Swagger
Para acceder a la documentación de Swagger de cada microservicio, ve a la ruta:

http://<eureka-microservice-url>/swagger-ui/index.html

Base de Datos en Memoria H2
WasteManagerService
Para acceder a la consola H2 de WasteManagerService:
URL: http://<eureka-microservice-url>/h2-console
Username: sa
Password: (deja este campo vacío)
URL JDBC: jdbc:h2:mem:waste_manager_db


WasteManagerAddressService
Para acceder a la consola H2 de WasteManagerAddressService:
URL: http://<eureka-microservice-url>/h2-console
Username: sa
Password: (deja este campo vacío)
URL JDBC: jdbc:h2:mem:address_db

Nota
Se están ultimando detalles para el correcto funcionamiento de los microservicios con la configuración centralizada. El repositorio que se está preparando para esta configuración está en https://github.com/aaliagab/WasteManagementConfigurations

Recuerda sustituir <eureka-microservice-url> por la URL real del microservicio que deseas consultar, por ejemplo, http://localhost:8765 para eureka