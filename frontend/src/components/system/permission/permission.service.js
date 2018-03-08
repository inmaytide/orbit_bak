import axios from 'axios'

const PERMISSION_API = {
  getData: process.env.API_ROOT + 'sys/permissions'
}

export const Categories = [
  {'label': 'Menu', 'value': 'MENU'},
  {'label': 'Function', 'value': 'FUNCTION'}
]

export const HttpMethods = [
  {'label': 'GET', 'value': 'GET'},
  {'label': 'HEAD', 'value': 'HEAD'},
  {'label': 'POST', 'value': 'POST'},
  {'label': 'PUT', 'value': 'PUT'},
  {'label': 'PATCH', 'value': 'PATCH'},
  {'label': 'DELETE', 'value': 'DELETE'}
]

export class PermissionService {
  getData () {
    return axios.get(PERMISSION_API.getData)
      .then(res => res.data)
      .catch(err => Promise.reject(err))
  }
  getParentOptions () {
    return axios.get(PERMISSION_API.getData, {
      params: {
        'category': 'MENU'
      }
    })
      .then(res => this.transformToOptions(res.data))
      .catch(err => Promise.reject(err))
  }
  transformToOptions (list) {
    let options = []
    list.forEach(element => {
      options.push({label: element.name, value: element.id, children: this.transformToOptions(element.children)})
    })
    return options
  }
}
