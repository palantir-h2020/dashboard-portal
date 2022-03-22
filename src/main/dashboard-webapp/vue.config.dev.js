module.exports = {
  transpileDependencies: ["vuetify", "tiptap-vuetify"],
  devServer: {
    proxy: {
      "^/api": {
        target: "http://localhost:8081",
        ws: true,
        changeOrigin: true
      }
    },
    port: 8080
  }
};
