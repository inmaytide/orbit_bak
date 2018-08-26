<template>
  <div>
    <CellGroup @on-click="select">
        <Poptip placement="bottom-end" style="float: right;" :width="300" :show-message="false">
          <div slot="content">
            <Form ref="current" :model="current" :label-width="45">
              <FormItem label="Name">
                <Input v-model="current.name" size="small"></Input>
              </FormItem>
              <FormItem label="Code">
                <Input v-model="current.code"  size="small"></Input>
              </FormItem>
              <FormItem style="text-align: right;">
                <Button type="primary" size="small">{{$t('common.btn.save')}}</Button>
                <Button style="margin-left: 8px" size="small">{{$t('common.btn.cancel')}}</Button>
              </FormItem>
            </Form>
          </div>
          <Cell style="display:flex; justify-content: center;">
            <icon type="md-add" style="color: #2d8cf0;"/>
            <a style="padding: 1px 0; user-select: none; font-size: 12px;" href="javascript:void(0);">{{$t('common.btn.new')}}</a>
          </Cell>
        </Poptip>
      <Cell :selected="selected === element.id"
            v-for="element in elements"
            :key="element.id"
            :title="element.name"
            :label="element.code"
            :name="element.id">
        <a v-if="selected === element.id" slot="extra" style="padding: 1px 5px; color: red;">{{$t('common.btn.remove')}}</a>
      </Cell>
    </CellGroup>
  </div>
</template>
<script>
import api from '../../../apis/modules/system/func.js';

export default {
  name: 'functions',
  props: {
    menuId: String
  },
  data () {
    return {
      elements: [],
      selected: '',
      current: {}
    };
  },
  watch: {
    menuId () {
      this.refresh();
    }
  },
  methods: {
    refresh () {
      if (!this.$commons.isNull(this.menuId)) {
        this.$http.get(api.listByMenuId(this.menuId))
          .then(res => (this.elements = res));
      }
    },
    select (id) {
      console.log(id);
      this.selected = id;
    }
  },
  mounted () {
    this.refresh();
  }
};
</script>
