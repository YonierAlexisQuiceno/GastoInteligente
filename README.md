# GastoInteligente - Aplicación Android con Sugar ORM

## Descripción del Proyecto

**GastoInteligente** es una aplicación móvil Android desarrollada en **Java** como Evidencia de Aprendizaje.  
Su propósito es demostrar el uso de una base de datos local (SQLite) a través de la librería **Sugar ORM**, implementando un CRUD completo (Crear, Leer, Actualizar, Eliminar) de usuarios, validaciones de seguridad, detección del ciclo de vida de la aplicación y un panel de administrador que utiliza **ListView** con **ArrayAdapter**.

---

## Tecnologías Utilizadas

| Tecnología | Descripción |
|---|---|
| **Java** | Lenguaje de programación para la lógica de negocio |
| **XML** | Diseño de interfaces gráficas (Layouts) |
| **Sugar ORM 1.5** | ORM para simplificar operaciones SQLite |
| **SQLite** | Base de datos local del dispositivo Android |
| **Material Design** | Componentes visuales modernos (MaterialCardView, TextInputLayout) |
| **Android SDK 36** | API objetivo (compatible desde API 24 / Android 7.0) |

---

## Estructura del Proyecto

```
app/src/main/
├── AndroidManifest.xml          ← Configuración de la app y metadata de Sugar ORM
├── java/com/example/gastointeligente/
│   ├── SplashActivity.java      ← Pantalla de bienvenida (5 segundos)
│   ├── LoginActivity.java       ← Inicio de sesión con validación
│   ├── RegisterActivity.java    ← Registro de cuenta (validación de campos)
│   ├── MainActivity.java        ← CRUD completo con Sugar ORM + Ciclo de vida
│   ├── AdminActivity.java       ← Panel de administrador (ListView + ArrayAdapter)
│   └── Usuario.java             ← Modelo de datos (SugarRecord → tabla SQLite)
└── res/layout/
    ├── activity_splash.xml      ← Layout del Splash
    ├── activity_login.xml       ← Layout del Login
    ├── activity_register.xml    ← Layout del Registro
    ├── activity_main.xml        ← Layout del CRUD (formulario + botones)
    └── activity_admin.xml       ← Layout del Panel Admin (ListView)
```

---

## Pantallas de la Aplicación

### 1. Splash Screen (`SplashActivity`)
- Pantalla de bienvenida con animación `fadeIn` sobre el nombre de la aplicación.
- Duración de **5 segundos** antes de redirigir automáticamente al Login.
- Implementación con `Handler` + `postDelayed()`.

### 2. Login (`LoginActivity`)
- Campos de correo y contraseña con componentes `TextInputEditText` de Material Design.
- Validación de campos vacíos antes de proceder.
- Credenciales de acceso para Administrador: **admin** / **admin**. Para usuarios regulares, cualquier otra combinación.
- Navegación al formulario principal (`MainActivity`) tras autenticación exitosa, inyectando el Rol correspondiente (`ADMIN` o `USER`).
- Enlace para ir a la pantalla de registro.
- Demuestra el método del ciclo de vida `onStart()`.

### 3. Registro (`RegisterActivity`)
- Formulario con campos de Nombre, Correo y Contraseña.
- Validación de que todos los campos estén completos antes de proceder.

### 4. Pantalla Principal - CRUD (`MainActivity`)
Esta es la pantalla central del proyecto. Implementa las **4 operaciones fundamentales** usando Sugar ORM:

| Operación | Botón | Método Sugar ORM | Descripción |
|---|---|---|---|
| **Create** | Registrar | `usuario.save()` | Crea un nuevo registro en la tabla `Usuario` |
| **Read** | Consultar | `Usuario.find()` | Busca por cédula y muestra nombre y teléfono |
| **Update** | Actualizar | `user.setNombre()` + `user.save()` | Modifica los datos (vía `android:onClick`) |
| **Delete** | Eliminar | `user.delete()` | Borra permanentemente (vía `android:onClick`) |

**Control de Roles (Usuario vs Administrador):**
- **Usuario Normal:** Solo puede ver los botones de Registrar y Consultar.
- **Administrador:** Tiene acceso total a las funciones CRUD (Actualizar, Eliminar) y al panel de visualización general de la base de datos.

**Manejo de errores:**  
Todas las conversiones numéricas están protegidas con bloques `try-catch` para prevenir el crash por `NumberFormatException` (por ejemplo, cuando se digita un número telefónico que excede el límite de `int` en Java: 2,147,483,647).

**Ciclo de vida de la Activity:**  
Se implementaron todos los métodos del ciclo de vida de Android con mensajes `Toast` y `Log.d()`:
- `onCreate()` → `onStart()` → `onResume()` → `onPause()` → `onStop()` → `onDestroy()`

### 5. Panel de Administrador (`AdminActivity`)
- Accesible desde el botón "Ver Panel de Administrador" en la pantalla principal.
- Usa `Usuario.listAll(Usuario.class)` para obtener todos los registros.
- Muestra los datos en un **ListView** usando un **ArrayAdapter**.
- Cada elemento de la lista muestra: `C.C: [cédula] - [nombre] ([teléfono])`.
- Detecta clicks en cada fila con `OnItemClickListener`.

---

## Modelo de Datos

### Clase `Usuario` (extiende `SugarRecord`)

| Atributo | Tipo | Descripción |
|---|---|---|
| `cedula` | `int` | Número de cédula del usuario (clave de búsqueda) |
| `nombre` | `String` | Nombre completo del usuario |
| `telefono` | `int` | Número de teléfono del usuario |

Sugar ORM convierte automáticamente esta clase Java en una tabla SQLite llamada `USUARIO`, sin necesidad de escribir consultas SQL.

---

## Configuración de Sugar ORM

### `build.gradle.kts` (dependencia)
```kotlin
implementation("com.github.satyan:sugar:1.5")
```

### `AndroidManifest.xml` (metadata)
```xml
<application android:name="com.orm.SugarApp" ...>
    <meta-data android:name="DATABASE" android:value="gastointeligente.db" />
    <meta-data android:name="VERSION" android:value="1" />
    <meta-data android:name="QUERY_LOG" android:value="true" />
    <meta-data android:name="DOMAIN_PACKAGE_NAME" android:value="com.example.gastointeligente" />
</application>
```

---

## Cómo Ejecutar el Proyecto

1. Abrir el proyecto en **Android Studio** (versión Iguana o superior recomendada).
2. Verificar que el **Gradle JDK** esté configurado correctamente:
   - `File` → `Settings` → `Build, Execution, Deployment` → `Build Tools` → `Gradle`
   - En `Gradle JVM criteria` → `Version: 17`
3. Sincronizar el proyecto con Gradle (icono del elefante 🐘).
4. Ejecutar en un emulador o dispositivo físico con **API 24+** (Android 7.0 Nougat o superior).

### Credenciales de prueba (Administrador)
- **Usuario:** `admin`
- **Contraseña:** `admin`

---

## Verificación del CRUD (Pasos de prueba)

1. **Crear:** Digitar Nombre, Cédula y Teléfono → pulsar "Registrar".
2. **Consultar:** Limpiar todo, escribir solo la Cédula → pulsar "Consultar" → los campos se rellenan automáticamente.
3. **Actualizar:** Consultar primero, cambiar el nombre → pulsar "Actualizar" → volver a consultar para confirmar.
4. **Eliminar:** Escribir la cédula → pulsar "Eliminar" → intentar consultar de nuevo para confirmar que ya no existe.
5. **Admin:** Pulsar "Ver Panel de Administrador" → confirmar que la lista muestra todos los registros guardados.

---

## Depuración (Debugging)

- **Logcat:** Filtrar con la etiqueta `CICLO_VIDA` para ver los eventos del ciclo de vida en la consola.
- **App Inspection:** Usar la herramienta de Android Studio (`View` → `Tool Windows` → `App Inspection`) para inspeccionar la base de datos SQLite en tiempo real.
- **Toast Messages:** Cada operación CRUD y cambio de estado muestra un Toast visible en pantalla.

---

## Interfaz Gráfica (Screenshots)

![Banner](docs/screenshots/banner.png)

A continuación, algunas vistas de la aplicación:

| Pantalla de Login | Panel CRUD (Vista Principal) | Panel de Administrador |
|:---:|:---:|:---:|
| ![Login](docs/screenshots/panel%20de%20login.png) | ![Home](docs/screenshots/layout-home.png) | ![Admin](docs/screenshots/Panel%20de%20administracion.png) |

---

## Autor

Proyecto desarrollado como Evidencia de Aprendizaje para la asignatura de Programación Móvil.
