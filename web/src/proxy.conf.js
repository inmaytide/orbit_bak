const PROXY_CONFIG = {
  "/api": {
    "target": "https://api.orbit.com:7002",
    "secure": false,
    "pathRewrite": {
      "^/api": ""
    }
  }
}

module.exports = PROXY_CONFIG;
