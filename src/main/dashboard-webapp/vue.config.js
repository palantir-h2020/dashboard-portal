module.exports = {
  transpileDependencies: ["vuetify", "tiptap-vuetify"],
  devServer: {
    proxy: {
      "^/api": {
        target: "http://localhost:8081",
        ws: true,
        changeOrigin: true
      },
      "^/scc/api": {
        target: "http://sco-scc:8080",
        ws: true,
        changeOrigin: true
      },
      "^/so/api": {
        target: "http://sco-so:50101",
        ws: true,
        changeOrigin: true
      }
    },
    port: 8080
  },
  pluginOptions: {
    i18n: {
      locale: "en",
      fallbackLocale: "en",
      localeDir: "locales",
      enableInSFC: true
    }
  }
};
