import axios from 'axios'

const PERMISSION_API = {
  getData: process.env.API_ROOT + 'sys/permissions'
}

export default class {
  getData () {
    return axios.get(PERMISSION_API.getData)
      .then(res => res.data)
      .catch(err => Promise.reject(err))
  }
}
