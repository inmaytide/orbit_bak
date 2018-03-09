import axios from 'axios'

const PERMISSION_API = {
  getData: process.env.API_ROOT + 'sys/permissions',
  save: process.env.API_ROOT + 'sys/permissions',
  getIconOptions: process.env.API_ROOT + 'dictionaries?category=common.icon'
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
    inst.parent = parent && parent.length > 0 ? parent[parent.length - 1] : -1
    if (inst.parent !== -1) {
      inst.idPath = parent.join('-')
    }
    action(PERMISSION_API.save, inst)
      .then(res => console.log(res))
      .catch(err => Promise.reject(err))
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
