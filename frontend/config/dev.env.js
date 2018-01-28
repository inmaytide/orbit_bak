'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  API_ROOT: '"//127.0.0.1:7001/"',
  API_I18N: '"//127.0.0.1:7001/lang/"'
})
