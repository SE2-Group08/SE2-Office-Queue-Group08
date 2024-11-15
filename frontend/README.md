# React + TypeScript + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## Expanding the ESLint configuration

If you are developing a production application, we recommend updating the configuration to enable type aware lint rules:

- Configure the top-level `parserOptions` property like this:

```js
export default tseslint.config({
  languageOptions: {
    // other options...
    parserOptions: {
      project: ['./tsconfig.node.json', './tsconfig.app.json'],
      tsconfigRootDir: import.meta.dirname,
    },
  },
})
```

- Replace `tseslint.configs.recommended` to `tseslint.configs.recommendedTypeChecked` or `tseslint.configs.strictTypeChecked`
- Optionally add `...tseslint.configs.stylisticTypeChecked`
- Install [eslint-plugin-react](https://github.com/jsx-eslint/eslint-plugin-react) and update the config:

```js
// eslint.config.js
import react from 'eslint-plugin-react'

export default tseslint.config({
  // Set the react version
  settings: { react: { version: '18.3' } },
  plugins: {
    // Add the react plugin
    react,
  },
  rules: {
    // other rules...
    // Enable its recommended rules
    ...react.configs.recommended.rules,
    ...react.configs['jsx-runtime'].rules,
  },
})
```
## API 

- GET `api/v1/services`
  - **description**: retrieve all the services handled by the office
  - **request parameters**: *none*
  - **response**: `200 OK` (success)
  - **response body content**: 
    ```JSON
    [ 
      { "serviceId": "1",  "serviceName": "service1"},
      { "serviceId": "2",  "serviceName": "service2" },
      { "serviceId": "3",  "serviceName": "service3" }
    ]
    ```
  - **error response**:
    - `500 Internal Server Error` (generic error)


- POST `/api/v1/tickets/generate`
  - **description**: retrieve the wait list code for the service type selected by the customer 
  - **request parameters**: *none*
  - **request body**: 
    ```JSON
    { "serviceId": "1"}
    ```
  - **response**:`200 OK`
  - **response body content**: 
    ```JSON
    { 
      "ticketID":"1-PKG",
      "waitingTime": "01h30m40s" 
    }
    ```
  - **error response**:
     - `500 Internal Server Error` (generic error)

- POST `api/customer/next`
  - **description**: retrieve the next customer to be handled by the officer
  - **request parameters**: *none*
  - **request body**: 
    ```JSON
    { 
      "counterID":3
    }
    ```
  - **response**: `200 OK`
  - **response body content**:
    ```JSON
    { 
      "ticketID":1,
      "counterID":3
    }
    ```
  - **error response**:
    - `500 Internal Server Error` (generic error)

- GET `api/v1/services/queues/status`
  - **description**: retrieve the queues length for each servuce type
  - **request parameters**: *none*
  - **response**: `200 OK`
  - **response body content**:
    ```JSON
    [{ 
      "serviceType": "service1",
      "queueLength": 2
    },
    {
      "serviceType": "service2",
      "queueLength": 2
    }]
    ```
  - **error response**:
    - `500 Internal Server Error` (generic error)


