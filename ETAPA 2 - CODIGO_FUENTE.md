---
**PROYECTO:** Sistema Web ERP en la nube - OMCGC  
**EMPRESA:** WALOOK MÉXICO, S.A. de C.V.  
**DOCUMENTO:** ETAPA 2 - Repositorio de Código Fuente Consolidado  
**VERSIÓN:** 1.0  
**FECHA:** 15 de febrero de 2026  
**AUTOR:** Ing. Gabriel Amilcar Cruz Canto  

---

# 💻 ETAPA 2 - CÓDIGO FUENTE (REPOSITORIO CONSOLIDADO)


## 📁 ESTRUCTURA DE ARCHIVOS (MAVEN ESTRUC)

```text
omcgc/
├── backend/
│   └── src/main/java/com/omcgc/erp/
│       ├── controller/
│       │   ├── ClienteController.java
│       │   └── ProveedorController.java
│       ├── model/
│       │   ├── Paciente.java
│       │   └── Proveedor.java
│       ├── repository/
│       │   ├── PacienteRepository.java
│       │   └── ProveedorRepository.java
│       └── service/
│           ├── ClienteService.java
│           └── ProveedorService.java
└── frontend/
    └── assets/js/
        ├── clientes-service.js
        ├── proveedores-service.js
        └── inventarios-service.js
```

---

## ☕ BACKEND (JAVA / SPRING BOOT)

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/model/Paciente.java

```java
/*
============================================================
Nombre del archivo : Paciente.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/Paciente.java
Tipo              : Backend (Modelo/Entidad Java)
Versión           : v1.0
Propósito         : Entidad POJO que representa la tabla 'paciente' en la base de datos.
============================================================
*/
package com.omcgc.erp.model;

import java.sql.Timestamp;

public class Paciente {
    private String idPaciente;
    private String nombre; 
    private String apellidos;
    private String razonSocial; 
    private String telefono;
    private String email;

    // Datos Fiscales (Modulo Clientes)
    private String rfc;
    private String tipoPersona; 
    private String regimenFiscal;
    private String usoCfdi;
    private String domicilioFiscal;

    // Control
    private String estatus; 
    private String fusionadoCon;

    // Auditoría
    private Timestamp fechaRegistro;
    private Timestamp fechaModificacion;

    // Getters and Setters
    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    public String getTipoPersona() { return tipoPersona; }
    public void setTipoPersona(String tipoPersona) { this.tipoPersona = tipoPersona; }
    public String getRegimenFiscal() { return regimenFiscal; }
    public void setRegimenFiscal(String regimenFiscal) { this.regimenFiscal = regimenFiscal; }
    public String getUsoCfdi() { return usoCfdi; }
    public void setUsoCfdi(String usoCfdi) { this.usoCfdi = usoCfdi; }
    public String getDomicilioFiscal() { return domicilioFiscal; }
    public void setDomicilioFiscal(String domicilioFiscal) { this.domicilioFiscal = domicilioFiscal; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    public String getFusionadoCon() { return fusionadoCon; }
    public void setFusionadoCon(String fusionadoCon) { this.fusionadoCon = fusionadoCon; }
    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public Timestamp getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Timestamp fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    @Override
    public String toString() {
        return "Paciente [id=" + idPaciente + ", nombre=" + nombre + ", rfc=" + rfc + "]";
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/model/Proveedor.java

```java
/*
============================================================
Nombre del archivo : Proveedor.java
Ruta              : omcgc/backend/src/main/java/com/omcgc/erp/model/Proveedor.java
Tipo              : Backend (Entity)
Versión           : v1.0
Propósito         : Entidad que representa la tabla 'proveedor' en la base de datos.
============================================================
*/
package com.omcgc.erp.model;

import java.sql.Timestamp;

public class Proveedor {

    private String idProveedor;
    private String tipoPersona; 
    private String rfc;
    private String razonSocial;
    private String nombreComercial;
    private String contacto;
    private String telefono;
    private String email;
    private String condicionPago;
    private String estatus;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    public Proveedor() {}

    public String getIdProveedor() { return idProveedor; }
    public void setIdProveedor(String idProveedor) { this.idProveedor = idProveedor; }
    public String getTipoPersona() { return tipoPersona; }
    public void setTipoPersona(String tipoPersona) { this.tipoPersona = tipoPersona; }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCondicionPago() { return condicionPago; }
    public void setCondicionPago(String condicionPago) { this.condicionPago = condicionPago; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }
    public Timestamp getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(Timestamp actualizadoEn) { this.actualizadoEn = actualizadoEn; }

    @Override
    public String toString() {
        return "Proveedor{id='" + idProveedor + "', rfc='" + rfc + "', razonSocial='" + razonSocial + "'}";
    }
}
```

---

## 🎮 CONTROLLER & SERVICE (LÓGICA BACKEND)

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/controller/ClienteController.java

```java
package com.omcgc.erp.controller;

import com.omcgc.erp.model.Paciente;
import com.omcgc.erp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Paciente> listar(@RequestParam(required = false) String buscar, @RequestParam(required = false) String rfc, @RequestParam(required = false) String estatus) {
        return clienteService.buscarClientes(buscar, rfc, estatus);
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/service/ClienteService.java

```java
package com.omcgc.erp.service;

import com.omcgc.erp.model.Paciente;
import com.omcgc.erp.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> buscarClientes(String b, String r, String e) { return pacienteRepository.findByFiltros(b, r, e); }
    public Paciente obtenerPorId(String id) { return pacienteRepository.findById(id); }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/controller/ProveedorController.java

```java
package com.omcgc.erp.controller;

import com.omcgc.erp.model.Proveedor;
import com.omcgc.erp.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> getAll(@RequestParam(required = false) String buscar, @RequestParam(required = false) String estatus) {
        List<Proveedor> lista = (buscar != null) ? proveedorService.search(buscar) : (estatus != null ? proveedorService.findByEstatus(estatus) : proveedorService.findAll());
        return ResponseEntity.ok(lista);
    }
}
```

### 📄 omcgc/backend/src/main/java/com/omcgc/erp/service/ProveedorService.java

```java
package com.omcgc.erp.service;

import com.omcgc.erp.model.Proveedor;
import com.omcgc.erp.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll() { return proveedorRepository.findAll(); }
    public Proveedor create(Proveedor p) {
        proveedorRepository.save(p);
        return p;
    }
}
```

---

## 🌐 FRONTEND (JAVASCRIPT)

### 📄 omcgc/frontend/assets/js/clientes-service.js

```javascript
/*
============================================================
Nombre del archivo : clientes-service.js
Propósito         : Lógica de negocio y UI para el módulo de Clientes.
============================================================
*/
const ClientesService = {
    apiUrl: AppConfig.getEndpoint('clientes'),
    permisosActuales: { ver: false, crear: false, editar: false, eliminar: false },

    init: async function () {
        this.currentUser = AuthService.getCurrentUser();
        if (!this.currentUser) { window.location.href = 'login.html'; return; }
        this.permisosActuales = AuthService.obtenerPermisosModulo("CLIENTES");
        if (!this.permisosActuales.ver) {
            alert("Acceso Denegado");
            window.location.href = 'menu.html';
            return;
        }
        this.cargarClientes();
        this.setupEventListeners();
    },

    cargarClientes: async function () {
        const buscar = document.getElementById('btnBuscar').value;
        const rfc = document.getElementById('filterRfc').value;
        const query = `?buscar=${encodeURIComponent(buscar)}&rfc=${encodeURIComponent(rfc)}`;
        const response = await fetch(`${this.apiUrl}${query}`, {
            headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
        });
        const data = await response.json();
        this.renderTabla(data);
    },

    renderTabla: function (clientes) {
        const tbody = document.getElementById('clientesTableBody');
        tbody.innerHTML = '';
        clientes.forEach(c => {
            const tr = document.createElement('tr');
            tr.onclick = () => this.verDetalle(c.idPaciente);
            tr.innerHTML = `
                <td>${c.nombre}</td>
                <td>${c.rfc || '--'}</td>
                <td>${c.telefono || 'Sin contacto'}</td>
                <td>${c.estatus}</td>
                <td><button onclick="event.stopPropagation(); ClientesService.verDetalle('${c.idPaciente}')">Ver</button></td>
            `;
            tbody.appendChild(tr);
        });
    }
};
```

### 📄 omcgc/frontend/assets/js/proveedores-service.js

```javascript
/*
============================================================
Nombre del archivo : proveedores-service.js
Propósito         : Lógica de negocio y UI para el módulo de Proveedores.
============================================================
*/
const ProveedoresService = {
    apiUrl: AppConfig.getEndpoint('proveedores'),
    permisosActuales: {},

    init: async function () {
        this.currentUser = AuthService.getCurrentUser();
        this.permisosActuales = AuthService.obtenerPermisosModulo("PROVEEDORES");
        if (!this.permisosActuales.ver) {
            alert("Acceso Denegado");
            window.location.href = 'menu.html';
            return;
        }
        await this.cargarProveedores();
    },

    cargarProveedores: async function () {
        const url = `${this.apiUrl}?_=" + new Date().getTime();
        const response = await fetch(url, {
            headers: { 'Authorization': `Bearer ${sessionStorage.getItem('token_erp')}` }
        });
        const data = await response.json();
        this.proveedores = data || [];
        this.renderTabla();
    },

    renderTabla: function () {
        const tbody = document.getElementById('proveedoresTableBody');
        tbody.innerHTML = '';
        this.proveedores.forEach(p => {
            const tr = document.createElement('tr');
            tr.onclick = () => this.verDetalle(p);
            tr.innerHTML = `
                <td>${p.nombreComercial || p.razonSocial}</td>
                <td>${p.rfc || '--'}</td>
                <td>${p.estatus}</td>
                <td><button onclick="event.stopPropagation(); ProveedoresService.verDetallePorId('${p.idProveedor}')">Ver</button></td>
            `;
            tbody.appendChild(tr);
        });
    }
};
```

### 📄 omcgc/frontend/assets/js/inventarios-service.js

```javascript
/*
============================================================
Nombre del archivo : inventarios-service.js
Propósito         : Gestión de inventarios, cálculos de utilidad y kardex.
============================================================
*/
const InventariosService = {
    apiUrl: AppConfig.getEndpoint('productos') || '/api/productos',
    productos: [],

    init: async function () {
        this.currentUser = AuthService.getCurrentUser();
        this.permisosActuales = AuthService.getPermisosModulo('inventarios') || { ver: true };
        this.bindEvents();
        await this.fetchProductos();
    },

    bindEvents: function () {
        document.getElementById('costoUnitario')?.addEventListener('input', () => this.calcularPrecioVenta());
        document.getElementById('porcentajeUtilidad')?.addEventListener('input', () => this.calcularPrecioVenta());
    },

    fetchProductos: async function () {
        // Simulación de datos (Mocks para prototipo Etapa 2)
        this.productos = [
            { sku: '7500001', nombre: 'Armazón Classic RB3016', marca: 'Ray-Ban', categoria: 'ARMAZONES', existencia: 43, minimo: 20 },
            { sku: '7500002', nombre: 'Lente CR-39 1.50', marca: 'Genérico', categoria: 'LENTES', existencia: 120, minimo: 50 }
        ];
        this.renderTabla(this.productos);
    },

    calcularPrecioVenta: function () {
        const costo = parseFloat(document.getElementById('costoUnitario').value) || 0;
        const utilidad = parseFloat(document.getElementById('porcentajeUtilidad').value) || 0;
        const precio = costo * (1 + (utilidad / 100));
        document.getElementById('precioVenta').value = precio.toFixed(2);
    }
};
```
