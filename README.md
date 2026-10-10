# ⚙️ Evaluaciones Servidor (Backend Concurrente en Java)

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Networking](https://img.shields.io/badge/Sockets-TCP%20Server-blue?style=for-the-badge)]()
[![Architecture](https://img.shields.io/badge/Multithreading-Concurrent-success?style=for-the-badge)]()

Servidor backend multihilo desarrollado en **Java** con Maven para la orquestación, validación de credenciales, recepción y calificación concurrente de evaluaciones académicas en tiempo real.

Diseñado como motor central para la suite de escritorio [Taller2-SistemaEvaluacion](https://github.com/gavafue/Taller2-SistemaEvaluacion).

---

## 🎯 Capacidades Técnicas

* 🧵 **Arquitectura Concurrente:** Servidor de sockets TCP capaz de atender múltiples clientes de forma paralela sin bloqueos.
* 📋 **Protocolo de Comandos Estructurado:** Procesamiento de solicitudes de autenticación, alta de usuarios, envío de respuestas y consulta de historiales.
* 💾 **Almacenamiento y Persistencia:** Gestión de historiales de exámenes, contraseñas de usuarios y bancos de preguntas.
* 🛡️ **Tolerancia a Fallos:** Manejo robusto de excepciones de red, desconexiones intempestivas y validación de sintaxis de mensajes.

---

## 🚀 Despliegue y Ejecución

```bash
# Clonar el repositorio
git clone https://github.com/gavafue/evaluacionesServidor.git
cd evaluacionesServidor

# Compilar el proyecto con Maven
mvn clean package

# Iniciar el servidor
mvn exec:java
```

---

## 👨‍💻 Autor

* **Gabriel Vázquez** — *Profesor de Informática (INET) & Full Stack Developer*
* [LinkedIn](https://www.linkedin.com/in/gavafue) · [CVUy](https://export.cvuy.uy/cv/?1ef08a58a0c81bcd4d399b62513ffa9a)
