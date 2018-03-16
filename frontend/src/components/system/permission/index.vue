<template>
  <div class="wrapper">
    <Breadcrumb class="breadcrumb">
        <BreadcrumbItem>{{$t('menu.home')}}</BreadcrumbItem>
        <BreadcrumbItem>{{$t('menu.system')}}</BreadcrumbItem>
        <BreadcrumbItem>{{$t('menu.system.permission')}}</BreadcrumbItem>
    </Breadcrumb>
    <div class="toolbar">
      <Button type="primary" icon="plus" size="small" @click="add()">{{$t('common.func.add')}}</Button>
    </div>
    <div class="list">
      <zk-table
        :columns="columns"
        :data="list"
        :selection-type="false"
        :tree-type="true"
        :expand-type="false"
        :border="true"
        :empty-text="$t('table.no.data')"
        row-class-name="line">
        <template slot="category" slot-scope="scope">
          {{categoryFilter(scope.row.category)}}
        </template>
        <template slot="ops" slot-scope="scope">
          <Button class="operations" type="primary" size="small" @click="edit(scope.row)">{{$t('common.func.edit')}}</Button>
          <Button class="operations" type="success" size="small" @click="move('up', scope.row)">{{$t('permission.func.move.up')}}</Button>
          <Button class="operations" type="success" size="small" @click="move('down', scope.row)">{{$t('permission.func.move.down')}}</Button>
          <Button v-if="scope.row.children.length == 0" class="operations" type="error" size="small" @click="remove(scope.row)">{{$t('common.func.remove')}}</Button>
        </template>
      </zk-table>
    </div>
    <Modal
        v-model="showDetails"
        :width="700"
        :title="$t('permission.modal.title')"
        :mask-closable="false">
      <Form ref="instance" :model="instance" :rules="rules" :label-width="80">
        <FormItem :label="$t('permission.column.parent')" prop="parent">
          <Cascader
            :placeholder="$t('common.select.placeholder')"
            :data="parentOptions"
            v-model="instance.parent"
            :change-on-select="true"></Cascader>
        </FormItem>
        <Row>
          <i-col span="12">
            <FormItem :label="$t('permission.column.code')" prop="code">
              <i-input v-model="instance.code" :maxlength="32"></i-input>
            </FormItem>
          </i-col>
          <i-col span="12">
            <FormItem :label="$t('permission.column.name')" prop="name">
              <i-input v-model="instance.name" :maxlength="64"></i-input>
            </FormItem>
        </i-col>
        </Row>
        <Row>
          <i-col span="12">
            <FormItem :label="$t('permission.column.category')" prop="category">
              <i-select v-model="instance.category">
                <Option v-for="item in categories" :value="item.value" :label="item.label" :key="item.value"></Option>
              </i-select>
            </FormItem>
          </i-col>
          <i-col span="12">
            <FormItem :label="$t('permission.column.method')" prop="method">
              <i-select v-model="instance.method">
                <Option v-for="item in httpMethods" :value="item.value" :label="item.label" :key="item.value"></Option>
              </i-select>
            </FormItem>
          </i-col>
        </Row>
        <Row>
          <i-col span="16">
            <FormItem :label="$t('permission.column.action')" prop="action">
              <i-input v-model="instance.action" :maxlength="256"></i-input>
            </FormItem>
          </i-col>
          <i-col span="8">
            <FormItem :label="$t('permission.column.icon')" prop="icon">
              <i-select v-model="instance.icon" :placeholder="$t('common.select.placeholder')">
                <Option v-for="item in iconOptions" :value="item.id" :key="item.id" :label="item.text"></Option>
              </i-select>
            </FormItem>
          </i-col>
        </Row>
        <FormItem :label="$t('permission.column.description')" prop="description">
          <i-input v-model="instance.description" type="textarea" :rows="2" :maxlength="256"></i-input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button @click="cancel">{{$t('permission.modal.actions.cancel')}}</Button>
        <Button type="primary" @click="save">{{$t('permission.modal.actions.save')}}</Button>
      </div>
    </Modal>
  </div>
</template>
<style>
@import url('../../../../static/css/content.css');

.line {
  padding: 3px 5px!important;
  height: 25px!important;
}

.zk-table__cell-inner {
  padding: 5px 10px!important;
}
.zk-checkbox-wrapper {
  padding-left: 8px;
}
.zk-icon-plus-square-o:before {
  content: '\E633'!important;
}
.zk-icon-minus-square-o:before {
  content: '\E633'!important;
  display:inline-block;
  transform: rotate(90deg);
}
</style>
<script>
import {PermissionService, Categories, HttpMethods} from './permission.service'
import commons from '../../../commons'
export default {
  name: 'PermissionIndex',
  created: function () {
    this.service = new PermissionService()
    this.refresh()
    this.service.getIconOptions()
      .then(res => (this.iconOptions = res))
      .catch(err => commons.errorHandler(err))
  },
  data () {
    var validCodeExist = (rule, value, callback) => {
      this.service.validCode(value, this.instance)
        .then(res => {
          if (res.isRepeat) {
            callback()
          } else {
            callback(new Error(this.$i18n.t('permission.validator.name.not.empty')))
          }
        })
        .catch(err => {
          callback(new Error(err))
        })
    }
    return {
      columns: [
        {label: this.$i18n.t('permission.column.name'), prop: 'name', width: '25%'},
        {label: this.$i18n.t('permission.column.code'), prop: 'code', width: '20%'},
        {label: this.$i18n.t('permission.column.category'), type: 'template', width: '10%', template: 'category'},
        {label: this.$i18n.t('common.func.operations'), type: 'template', width: '45%', template: 'ops'}
      ],
      list: [],
      showDetails: false,
      instance: {
        category: 'MENU',
        method: 'GET'
      },
      parentOptions: [],
      iconOptions: [],
      categories: Categories,
      httpMethods: HttpMethods,
      rules: {
        code: [
          {required: true, message: this.$i18n.t('permission.validator.code.not.empty'), trigger: 'blur'},
          {validator: validCodeExist, message: this.$i18n.t('permission.validator.code.not.repeat'), trigger: 'blur'}
        ],
        name: [
          {required: true, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    refresh: function () {
      this.service.getData().then(res => (this.list = res))
      this.service.getParentOptions().then(res => (this.parentOptions = res))
    },
    add: function () {
      this.instance = {method: 'GET', category: 'MENU'}
      this.showDetails = true
    },
    move: function (category, inst) {
      this.service.move(category, inst.id).then(() => this.refresh())
    },
    remove: function (inst) {
      this.$Modal.confirm({
        title: this.$i18n.t('layer.title.prompt'),
        content: this.$i18n.t('permission.remove.confirm.message'),
        okText: this.$i18n.t('layer.confirm.btn.ok'),
        cancelText: this.$i18n.t('layer.confirm.btn.cancel'),
        onOk: () => {
          this.service.remove(inst.id)
            .then(() => this.refresh())
        }
      })
    },
    edit: function (inst) {
      inst.parent = inst.idPath === null ? [] : inst.idPath.split('-')
      this.instance = inst
      this.showDetails = true
    },
    cancel: function () {
      this.showDetails = false
    },
    categoryFilter: function (category) {
      const filtered = Categories.filter(c => c.value === category)
      return filtered && filtered.length > 0 ? filtered[0].label : ''
    },
    save: function () {
      this.$refs['instance'].validate()
        .then(valid => {
          if (valid) {
            this.service.save(this.instance)
              .then(res => {
                this.refresh()
                this.cancel()
              })
          }
        })
    }
  }
}
</script>
