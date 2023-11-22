const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/bills/crear',  // Cambia esto según tu ruta de backend
    createProxyMiddleware({
      target: 'http://localhost:9090',
      changeOrigin: true,
      pathRewrite: {'^/bills/crear' : '/bills/crear'},  // Agregamos esta opción para reescribir la ruta
    })
  );
};
