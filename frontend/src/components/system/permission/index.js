import {PermissionService, Categories, HttpMethods} from './permission.service'
import commons from '../../../commons'
export default {
  name: 'PermissionIndex',
  created: function () {
    this.service = new PermissionService()
    this.service.getData()
      .then(res => (this.list = res))
      .catch(err => commons.errorHandler(err))
    this.service.getParentOptions()
      .then(res => (this.parentOptions = res))
      .catch(err => commons.errorHandler(err))
  },
  data () {
    return {
      columns: [
        {label: this.$i18n.t('permission.column.name'), prop: 'name', width: '25%'},
        {label: this.$i18n.t('permission.column.code'), prop: 'code', width: '20%'},
        {label: this.$i18n.t('permission.column.category'), prop: 'category', width: '10%'},
        {label: this.$i18n.t('common.func.operations'), type: 'template', width: '45%', template: 'ops'}
      ],
      list: [],
      showDetails: false,
      instance: {
        category: 'MENU',
        method: 'GET'
      },
      parentOptions: [],
      categories: Categories,
      httpMethods: HttpMethods,
      rules: {
        code: [
          {required: true, message: this.$i18n.t('permission.validator.code.not.empty'), trigger: 'blur'}
        ],
        name: [
          {required: true, message: this.$i18n.t('permission.validator.name.not.empty'), trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    add: function () {
      this.instance = {method: 'GET', category: 'MENU'}
      this.showDetails = true
    },
    edit: function (inst) {
      inst.parent = ['392062345830076416', '392062572091805696']
      this.instance = inst
      this.showDetails = true
    },
    cancel: function () {
      this.showDetails = false
    },
    save: function () {
      this.$refs['instance'].validate()
        .then(valid => {
          console.log(valid)
          console.log(this.instance)
        })
    }
  }
}
