# Infraestructura AWS con Terraform - API de Franquicias

## 📋 Descripción General
Este directorio contiene la configuración de **Infraestructura como Código (IaC)** utilizando Terraform para desplegar los recursos de AWS necesarios para la API de Franquicias.

### Recursos Creados
| Recurso | Tipo | Descripción |
|---------|------|-------------|
| **RDS MySQL** | `aws_db_instance.franchise_db` | Instancia de base de datos MySQL 8.0.40 (clase `db.t3.micro`, Free Tier elegible) |
| **Security Group** | `aws_security_group.mysql_sg` | Grupo de seguridad que permite conexiones entrantes al puerto 3306 |

## 📁 Estructura de Archivos
```
terraform/
├── main.tf               # Configuración principal de recursos AWS
├── variables.tf          # Variables parametrizadas (región, contraseña)
├── outputs.tf            # Valores de salida (endpoint, puerto)
├── screenshots/          # 📸 Evidencias de la implementación
│   ├── 01-rds-console.png
│   ├── 02-rds-details.png
│   ├── 03-terraform-output.png
│   └── 04-mysql-data.png
└── README.md             # Este archivo
```

## 🚀 Comandos de Despliegue Ejecutados

### 1. Inicializar Terraform
```bash
terraform init
```
*Descarga los proveedores necesarios (AWS)*

### 2. Validar la Configuración
```bash
terraform validate
```
*Verifica que la sintaxis sea correcta*

### 3. Ver el Plan de Ejecución
```bash
terraform plan
```
*Muestra los recursos que se crearán*

### 4. Aplicar la Configuración
```bash
terraform apply -auto-approve
```
*Crea los recursos en AWS (aprox. 5-8 minutos)*

### 5. Ver los Outputs
```bash
terraform output
```
*Muestra el endpoint y puerto de la base de datos*

## ✅ Estado Actual de la Infraestructura

### Especificaciones de la Base de Datos RDS
| Atributo | Valor |
|:---|:---|
| **Identificador** | `franchise-database` |
| **Endpoint** | `franchise-database.ca9c2sq4agic.us-east-1.rds.amazonaws.com` |
| **Puerto** | `3306` |
| **Motor** | MySQL 8.0.40 |
| **Clase de Instancia** | `db.t3.micro` (Free Tier) |
| **Almacenamiento** | 20 GB (`gp3`) |
| **Base de Datos por Defecto** | `franchise_db` |
| **Usuario Maestro** | `admin` |
| **Backup Retention** | 1 día |

### Configuración de Red
| Atributo | Valor |
|:---|:---|
| **Security Group ID** | `sg-0d6a4a0ea55788682` |
| **Nombre Security Group** | `franchise-mysql-sg` |
| **Regla de Entrada** | Puerto `3306` abierto para desarrollo |
| **Acceso Público** | Habilitado (para pruebas locales) |

## 📸 Evidencias de Implementación

### 1. Instancia RDS en Consola AWS
*La instancia `franchise-database` creada y en estado **Available**.*

**Archivo:** `screenshots/01-rds-console.png`

### 2. Detalles de la Instancia RDS
*Endpoint, motor, clase de instancia y almacenamiento.*

**Archivo:** `screenshots/02-rds-details.png`

### 3. Salida del Comando `terraform output`
*Verificación desde línea de comandos.*

**Archivo:** `screenshots/03-terraform-output.png`

```
$ terraform output
rds_endpoint = "franchise-database.ca9c2sq4agic.us-east-1.rds.amazonaws.com"
rds_port = 3306
```

### 4. Verificación de Datos desde Cliente MySQL
*Conexión directa y consulta de datos iniciales.*

**Archivo:** `screenshots/04-mysql-data.png`

```sql
mysql> SELECT * FROM franchise;
+----+------------------+
| id | name             |
+----+------------------+
|  1 | Franchise Norte  |
|  2 | Franchise Centro |
+----+------------------+
```

## 🔒 Gestión de Secretos y Seguridad

### Archivos Excluidos (`.gitignore`)
Por razones de seguridad y límites de GitHub, los siguientes archivos están excluidos del repositorio:

```gitignore
# Terraform
**/.terraform/          # Proveedores binarios (>100MB)
*.tfstate               # Estado local (contiene información sensible)
*.tfstate.*             # Backups del estado
terraform.tfvars        # Variables con valores reales
crash.log               # Logs de errores
```
Recursos

Para evitar costos adicionales en AWS, cuando ya no se necesite la infraestructura:

```bash
terraform destroy -auto-approve
```

Este comando eliminará:
- La instancia RDS `franchise-database`
- El Security Group `franchise-mysql-sg`

## 🔄 Integración con la API Local

La API de Franquicias (desplegada localmente con Docker) se conecta a esta base de datos usando:

```properties
spring.datasource.url=jdbc:mysql://franchise-database.ca9c2sq4agic.us-east-1.rds.amazonaws.com:3306/franchise_db
spring.datasource.username=admin
spring.datasource.password=Admin123!
```

## 📚 Notas Técnicas

### Limitaciones del Free Tier Consideradas
- **Backup retention**: Máximo 1 día (configurado)
- **Almacenamiento**: 20 GB (configurado)
- **Clase de instancia**: `db.t3.micro` (única gratis)

### Outputs Definidos
| Output | Descripción | Uso |
|--------|-------------|-----|
| `rds_endpoint` | Endpoint de conexión a la BD | Configuración de la API [citation:2] |
| `rds_port` | Puerto de conexión (3306) | Configuración de la API [citation:2] |

