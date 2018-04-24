import axios from 'axios'

const ORGANIZATION_API = {
  list: process.env.API_ROOT + 'sys/organizations',
  save: process.env.API_ROOT + 'sys/permissions',
  remove: process.env.API_ROOT + 'sys/permissions/',
  validCode: process.env.API_ROOT + 'sys/permissions/checkCode/',
  move: process.env.API_ROOT + 'sys/permissions/move/'
}

export const Categories = [
  {'label': 'Client', 'value': 'client'},
  {'label': 'Company', 'value': 'company'},
  {'label': 'Factory', 'value': 'factory'},
  {'label': 'Department', 'value': 'department'},
  {'label': 'Group', 'value': 'group'}
]

export class OrganizationService {
  save (inst) {
    const action = inst.id === undefined || inst.id === -1 ? axios.post : axios.put
    const parent = inst.parent
    const data = Object.assign({}, inst, {parent: parent && parent.length > 0 ? parent[parent.length - 1] : -1})
    if (data.parent !== -1) {
      data.idPath = parent.join('-')
    }
    return action(ORGANIZATION_API.save, data)
  }
  validCode (code, inst) {
    const id = (inst.id === null || inst.id === undefined) ? -1 : inst.id
    return axios.get(ORGANIZATION_API.validCode + id + '/' + code)
  }
  remove (id) {
    return axios.delete(ORGANIZATION_API.remove + id)
  }
  move (category, id) {
    return axios.patch(ORGANIZATION_API.move + id + '/' + category)
  }
  getData (parent) {
    let api = ORGANIZATION_API.list
    if (parent !== undefined && parent !== null) {
      api += '?parent=' + parent
    }
    return axios.get(api)
  }
  getParentOptions () {
    return axios.get(ORGANIZATION_API.getData, {
      params: {
        'category': 'MENU'
      }
    }).then(res => this.transformToOptions(res))
  }
  transformToOptions (list) {
    let options = []
    list.forEach(element => {
      options.push({label: element.name, value: element.id, children: this.transformToOptions(element.children)})
    })
    return options
  }
}
