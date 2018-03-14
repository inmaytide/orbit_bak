import axios from 'axios'

const PERMISSION_API = {
  getData: process.env.API_ROOT + 'sys/permissions',
  save: process.env.API_ROOT + 'sys/permissions',
  getIconOptions: process.env.API_ROOT + 'dictionaries?category=common.icon',
  remove: process.env.API_ROOT + 'sys/permissions/',
  validCode: process.env.API_ROOT + 'sys/permissions/checkCode/'
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
  save (inst) {
    const action = inst.id === undefined || inst.id === -1 ? axios.post : axios.put
    const parent = inst.parent
    const data = Object.assign({}, inst, {parent: parent && parent.length > 0 ? parent[parent.length - 1] : -1})
    if (data.parent !== -1) {
      data.idPath = parent.join('-')
    }
    return action(PERMISSION_API.save, data)
      .then(res => console.log(res))
      .catch(err => Promise.reject(err))
  }
  validCode (code, inst) {
    let api = PERMISSION_API.validCode
    api += (inst.id === null || inst.id === undefined) ? -1 : inst.id
    api += '/' + code
    return axios.get(api).then(res => res.data).catch(err => Promise.reject(err))
  }
  remove (id) {
    return axios.delete(PERMISSION_API.remove + id)
  }
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
    }).then(res => this.transformToOptions(res.data))
      .catch(err => Promise.reject(err))
  }
  getIconOptions () {
    return axios.get(PERMISSION_API.getIconOptions)
      .then(res => res.data)
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
