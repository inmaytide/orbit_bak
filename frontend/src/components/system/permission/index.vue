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
        row-class-name="line">
        <template slot="ops" slot-scope="scope">
          <Button class="operations" type="primary" size="small" @click="edit(scope.row)">{{$t('common.func.edit')}}</Button>
          <Button class="operations" type="success" size="small">{{$t('permission.func.move.up')}}</Button>
          <Button class="operations" type="success" size="small">{{$t('permission.func.move.down')}}</Button>
          <Button class="operations" type="error" size="small">{{$t('common.func.remove')}}</Button>
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
          <Cascader :data="parentOptions" v-model="instance.parent" :clearable="false"></Cascader>
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
              <i-select v-model="instance.icon"></i-select>
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
<script src="./index.js">
