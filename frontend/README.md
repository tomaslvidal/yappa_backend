# Yappa Frontend

Frontend de React.js para la aplicación de gestión de clientes Yappa.

## Tecnologías

- React 18
- React Router DOM
- Bootstrap 5 / React Bootstrap
- Axios
- Formik + Yup (validación de formularios)
- Vite (build tool)

## Requisitos Previos

- Node.js 18+ 
- npm o yarn

## Instalación

```bash
npm install
```

## Desarrollo

Para ejecutar el servidor de desarrollo:

```bash
npm run dev
```

La aplicación estará disponible en `http://localhost:3000`

## Build para Producción

```bash
npm run build
```

Los archivos compilados estarán en la carpeta `dist/`

## Docker

### Build de la imagen

```bash
docker build -t yappa-frontend .
```

### Ejecutar contenedor

```bash
docker run -p 80:80 yappa-frontend
```

## Características

- ✅ ABM completo de clientes (Alta, Baja, Modificación)
- ✅ Listado de clientes con búsqueda en tiempo real
- ✅ Validación de formularios con Formik y Yup
- ✅ Diseño responsive con Bootstrap 5
- ✅ Manejo de errores
- ✅ Confirmación de eliminación
- ✅ Navegación con React Router

## Estructura del Proyecto

```
frontend/
├── public/
├── src/
│   ├── components/
│   │   ├── common/        # Componentes reutilizables
│   │   └── layout/        # Layout (Navbar, Footer)
│   ├── pages/             # Páginas principales
│   ├── services/          # Servicios API
│   ├── App.jsx            # Componente principal
│   ├── main.jsx           # Entry point
│   └── index.css          # Estilos globales
├── Dockerfile
├── nginx.conf
├── vite.config.js
└── package.json
```

## API Endpoints

El frontend se comunica con los siguientes endpoints del backend:

- `GET /api/clientes` - Listar todos los clientes
- `GET /api/clientes/:id` - Obtener un cliente por ID
- `POST /api/clientes` - Crear un nuevo cliente
- `PUT /api/clientes/:id` - Actualizar un cliente
- `DELETE /api/clientes/:id` - Eliminar un cliente
- `GET /api/clientes/cuit/:cuit` - Buscar por CUIT
- `GET /api/clientes/email/:email` - Buscar por email
