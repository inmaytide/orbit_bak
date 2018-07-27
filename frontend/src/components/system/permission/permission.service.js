import axios from 'axios';

const PERMISSION_API = {
  getData: process.env.API_ROOT + 'sys/permissions',
  save: process.env.API_ROOT + 'sys/permissions',
  getIconOptions: process.env.API_ROOT + 'dictionaries?category=common.icon',
  remove: process.env.API_ROOT + 'sys/permissions/',
  validCode: process.env.API_ROOT + 'sys/permissions/checkCode/',
  move: process.env.API_ROOT + 'sys/permissions/move/'
};

export const Categories = [
  {'label': 'Menu', 'value': 'MENU'},
  {'label': 'Function', 'value': 'FUNCTION'}
];

export const HttpMethods = [
  {'label': 'GET', 'value': 'GET'},
  {'label': 'POST', 'value': 'POST'},
  {'label': 'PUT', 'value': 'PUT'},
  {'label': 'PATCH', 'value': 'PATCH'},
  {'label': 'DELETE', 'value': 'DELETE'},
  {'label': 'HEAD', 'value': 'HEAD'}
];

export class PermissionService {
  save (inst) {
    const action = inst.id === undefined || inst.id === -1 ? axios.post : axios.put;
    const parent = inst.parent;
    const data = Object.assign({}, inst, {parent: parent && parent.length > 0 ? parent[parent.length - 1] : -1});
    if (data.parent !== -1) {
      data.idPath = parent.join('-');
    }
    return action(PERMISSION_API.save, data);
  }
  validCode (code, inst) {
    const id = (inst.id === null || inst.id === undefined) ? -1 : inst.id;
    return axios.get(PERMISSION_API.validCode + id + '/' + code);
  }
  remove (id) {
    return axios.delete(PERMISSION_API.remove + id);
  }
  move (category, id) {
    return axios.patch(PERMISSION_API.move + id + '/' + category);
  }
  getData () {
    return axios.get(PERMISSION_API.getData);
  }
  getParentOptions () {
    return axios.get(PERMISSION_API.getData, {
      params: {
        'category': 'MENU'
      }
    }).then(res => this.transformToOptions(res));
  }
  getIconOptions () {
    return axios.get(PERMISSION_API.getIconOptions);
  }
  transformToOptions (list) {
    let options = [];
    list.forEach(element => {
      options.push({label: element.name, value: element.id, children: this.transformToOptions(element.children)});
    });
    return options;
  }
}
