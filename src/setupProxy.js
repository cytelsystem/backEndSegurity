const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  // Endpoint para POST /bills/crear
  app.use(
    '/bills/crear',
    createProxyMiddleware({
      target: 'http://localhost:9090',
      changeOrigin: true,
      pathRewrite: {'^/bills/crear' : '/bills/crear'},
    })
  );

  // Endpoint para GET /user/id
  app.use(
    '/users/adicionargrupo',
    createProxyMiddleware({
      target: 'http://localhost:9090',  // Cambia esto según tu ruta de backend
      changeOrigin: true,
      pathRewrite: {'^/users/adicionargrupo' : '/users/adicionargrupo'},
    })
  );
};