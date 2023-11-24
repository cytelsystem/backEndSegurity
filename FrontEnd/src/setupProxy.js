const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  // Endpoint para POST /event/crear
  app.use(
    '/event/crear',
    createProxyMiddleware({
      target: 'http://localhost:9090',
      changeOrigin: true,
      pathRewrite: {'^/event/crear' : '/event/crear'},
    })
  );

  // Endpoint para GET /users/adicionargrupo
  app.use(
    '/users/adicionargrupo',
    createProxyMiddleware({
      target: 'http://localhost:9090',  // Cambia esto según tu ruta de backend
      changeOrigin: true,
      pathRewrite: {'^/users/adicionargrupo' : '/users/adicionargrupo'},
    })
  );

  // Endpoint para GET /users/facturas/:userId
  app.use(
    '/users/facturas',
    createProxyMiddleware({
      target: 'http://localhost:9090',  // Cambia esto según tu ruta de backend
      changeOrigin: true,
      pathRewrite: {'^/users/facturas' : '/users/facturas'},
    })
  );
};