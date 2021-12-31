module.exports = {
  transpileDependencies: ["vuetify", "tiptap-vuetify"],
  devServer: {
    proxy: {
      "^/api": {
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true
      }
    },
    port: 8081
  }
};
