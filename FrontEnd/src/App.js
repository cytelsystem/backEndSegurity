import React, { useState, useEffect } from 'react';
import './App.css';
import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.min.css";
import '/node_modules/primeflex/primeflex.css'
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { httpClient } from './HttpClient';

import Keycloak from 'keycloak-js';

/*
  Init Options
*/
let initOptions = {
  url: 'http://localhost:8082/',
  realm: 'masterEvents',
  clientId: 'react-client',
}

let kc = new Keycloak(initOptions);

kc.init({
  onLoad: 'check-sso', // Supported values: 'check-sso' , 'login-required'
  checkLoginIframe: true,
  pkceMethod: 'S256'
}).then((auth) => {
  if (!auth) {
    // window.location.reload();
  } else {
    /* Remove below logs if you are using this on production */
    console.info("Authenticated");
    console.log('auth', auth)
    console.log('Keycloak', kc)
    console.log('Access Token', kc.token)


    /* http client will use this header in every request it sends */
    httpClient.defaults.headers.common['Authorization'] = `Bearer ${kc.token}`;

    kc.onTokenExpired = () => {
      console.log('token expired')
    }
  }
}, () => {
  /* Notify the user if necessary */
  console.error("Authentication Failed");
});

function App() {

  const [infoMessage, setInfoMessage] = useState('');
  const [userId, setUserId] = useState(null);
  const [userName, setUserName] = useState(null);

  useEffect(() => {
    // Use the Keycloak object to get the user ID after authentication
    if (kc.authenticated) {
      setUserName(kc.tokenParsed.preferred_username);
      setUserId(kc.tokenParsed.sub);
    }
  }, [kc.authenticated]);

  //************************************Crear Evento***********************************//
  const callBackend = () => {
    // const requestData = {
    //   customerBill: userId,
    //   productBill: "OTRA FACTURA DE JOAQUIN",
    //   totalPrice: 1500,
    // };
    const requestData = {
      idCompany: 1, // Reemplaza con el valor adecuado
      nameEvent: "Nombre del Evento",
      description: "Descripción del evento",
      location: "Ubicación del evento",
      city: "Ciudad del evento",
      date: "2023-11-23", // Reemplaza con la fecha adecuada en formato YYYY-MM-DD
      category: "Categoría del evento",
      minimumAge: 18, // Reemplaza con la edad mínima adecuada
      address: "Dirección del evento",
      openTime: "Hora de apertura del evento",
      images: ["link_imagen_1.jpg", "link_imagen_2.jpg"],
      tickets: [], // Puedes agregar datos de los tickets si es necesario
    };

    const config = {
      headers: {
        Authorization: `Bearer ${kc.token}`,
        'Content-Type': 'application/json;charset=UTF-8',
      }
    };

    httpClient.post('/api-event/SaveNewEvent', requestData, config)
    .then(response => {
      console.log('POST Request Successful:', response);
      // Handle the response as needed
    })
    .catch(error => {
      console.error('POST Request Error:', error);
      // Handle the error as needed
    });


  };


  //******************************Agregar el usuario al grupo CREAREVENTOS***************************** */

  const agregarGrupoCrearEventos = () => {

    console.log("Nombre usuario: ", userName)

    const requestData = {
      usuarioautenticado: userName,
      adicionaragrupo: "CREAREVENTOS",
    };

    const config = {
      headers: {
        Authorization: `Bearer ${kc.token}`,
        'Content-Type': 'application/json;charset=UTF-8',
      }
    };

    httpClient.post('/users/adicionargrupo', requestData, config)
    .then(response => {
      console.log('POST Request Successful:', response);
      // Handle the response as needed
    })
    .catch(error => {
      console.error('POST Request Error:', error);
      // Handle the error as needed
    });
  };


  //******************************Consultar facturas por id del usuario***************************** */

  // const consultarFacturasIdUsuario = () => {
  //   // Obtener el ID de usuario del estado (reemplaza 'tuUserId' con la variable que contiene el ID)
  //   // const userId = userId;

  //   // Configurar la solicitud
  //   const config = {
  //     headers: {
  //       Authorization: `Bearer ${kc.token}`,
  //     },
  //   };

  //   // Construir la URL del endpoint con el ID de usuario
  //   const endpointUrl = `/users/facturas/${userId}`;

  //   // Realizar la solicitud GET al endpoint
  //   httpClient.get(endpointUrl, config)
  //     .then(response => {
  //       console.log('GET Request Successful:', response.data);
  //       setInfoMessage(response.data)
  //       // Manejar la respuesta según sea necesario
  //     })
  //     .catch(error => {
  //       console.error('GET Request Error:', error);
  //       // Manejar el error según sea necesario
  //     });
  // };

  const consultarFacturasIdUsuario = () => {
    // Obtener el ID de usuario del estado (reemplaza 'tuUserId' con la variable que contiene el ID)
    // const userId = userId;

    // Configurar la solicitud
    const config = {
      headers: {
        Authorization: `Bearer ${kc.token}`,
      },
    };

    // Construir la URL del endpoint con el ID de usuario
    const endpointUrl = `/users/facturas/${userId}`;

    // Realizar la solicitud GET al endpoint
    httpClient.get(endpointUrl, config)
      .then(response => {
        console.log('GET Request Successful:', response.data);

        // Extraer el array de facturas de la respuesta
        const billsArray = response.data[0]?.bills || [];

        // Actualizar el estado con el array de facturas
        setInfoMessage(JSON.stringify(billsArray));

        // Manejar la respuesta según sea necesario
      })
      .catch(error => {
        console.error('GET Request Error:', error);
        // Manejar el error según sea necesario
      });
  };


  //********************************************************************************************** */





  return (
    <div className="App">
      <div className='grid'>
        <div className='col-12'>
          <h1>My Secured React App</h1>
        </div>
      </div>
      <div className="grid">

      </div>

      <div className='grid'>
        <div className='col-1'></div>
        <div className='col-2'>
          <div className="col">
            <Button onClick={() => { setInfoMessage(kc.authenticated ? 'Authenticated: TRUE' : 'Authenticated: FALSE') }}
              className="m-1 custom-btn-style"
              label='Is Authenticated' />

            <Button onClick={() => { kc.login() }}
              className='m-1 custom-btn-style'
              label='Login'
              severity="success" />

            <Button onClick={() => { setInfoMessage(kc.token) }}
              className="m-1 custom-btn-style"
              label='Show Access Token'
              severity="info" />

            <Button onClick={() => { setInfoMessage(JSON.stringify(kc.tokenParsed)) }}
              className="m-1 custom-btn-style"
              label='Show Parsed Access token'
              severity="warning" />

            <Button onClick={() => { setInfoMessage(kc.isTokenExpired(5).toString()) }}
              className="m-1 custom-btn-style"
              label='Check Token expired'
              severity="info" />

            <Button onClick={() => { kc.updateToken(10).then((refreshed) => { setInfoMessage('Token Refreshed: ' + refreshed.toString()) }, (e) => { setInfoMessage('Refresh Error') }) }}
              className="m-1 custom-btn-style"
              label='Update Token (if about to expire)' />  {/** 10 seconds */}

            <Button onClick={callBackend}
              className='m-1 custom-btn-style'
              label='Send HTTP Request'
              severity="success" />

            <Button onClick={() => { kc.logout({ redirectUri: 'http://localhost:3000/' }) }}
              className="m-1 custom-btn-style"
              label='Logout'
              severity="danger" />

            <Button onClick={() => { setInfoMessage(kc.hasRealmRole('admin').toString()) }}
              className="m-1 custom-btn-style"
              label='has realm role "Admin"'
              severity="info" />

            <Button onClick={() => { setInfoMessage(kc.hasResourceRole('test').toString()) }}
              className="m-1 custom-btn-style"
              label='has client role "test"'
              severity="info" />

            <Button onClick={() => { setInfoMessage(`User ID: ${userId}`) }}
              className="m-1 custom-btn-style"
              label='Get User ID'
              severity="info" />

            <Button
              onClick={agregarGrupoCrearEventos}
              className="m-1 custom-btn-style"
              label='Agregar usuario a CREAREVENTOS'
              severity="info" />

            <Button
              onClick={consultarFacturasIdUsuario}
              className="m-1 custom-btn-style"
              label='Consultar Facturas'
              severity="info" />


          </div>
        </div>
        <div className='col-6'>

          <Card>
            <p style={{ wordBreak: 'break-all' }} id='infoPanel'>
              {infoMessage}
            </p>
          </Card>
        </div>

        <div className='col-2'></div>
      </div>



    </div>
  );
}


export default App;
