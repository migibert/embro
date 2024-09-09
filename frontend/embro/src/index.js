import { Auth0Provider } from '@auth0/auth0-react';
import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
import config from './auth_config.json';

const container = document.getElementById('root');
const root = createRoot(container);
root.render(
  <Auth0Provider
    domain={config.domain}
    clientId={config.clientId}
    authorizationParams={{
      audience:config.audience,
      redirect_uri: window.location.origin
    }}
  >
    <App />
  </Auth0Provider>
);

