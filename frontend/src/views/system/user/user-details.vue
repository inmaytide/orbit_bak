<template>
  <div class="container-center form-user">
    <div class="container-title-bar">
      <div class="container-title">{{$t('common.title.' + status)}}{{$t('system.user.label.detail')}}</div>
      <div class="container-title-bar-actions">
        <Button shape="circle" type="info" @click="submit" :loading="submitting" v-if="editing">{{$t('common.btn.save')}}</Button>
        <Button shape="circle" type="info" @click="edit" v-if="!editing">{{$t('common.btn.edit')}}</Button>
        <Button shape="circle" @click="$router.back()">{{$t('common.btn.back')}}</Button>
      </div>
    </div>
    <div class="form-block">
      <Row>
        <Col span="4">
          <div class="container-avatar">
            <vue-core-image-upload
              :crop="'local'"
              :resize="'local'"
              :max-file-size="5242880"
              :headers="{Authorization: $commons.getAuthorization()}"
              :url="uploadApi"
              :cropRatio="'1:1'"
              @imageuploaded="imageuploaded">

              <div class="div-avatar" v-if="avatar == null">
                <Icon type="ios-add" size="40"/>
                <br/>{{$t('system.user.label.upload_avatar')}}
              </div>
              <img class="image-avatar" v-if="avatar != null" :src="avatarSrc"/>
            </vue-core-image-upload>
          </div>
        </Col>
        <Col span="20">
          <Form ref="basicInformation" :rules="baseInformationRules" :model="instance" :label-width="100">
            <Row>
              <Col span="16">
                <FormItem :label="$i18n.t('system.user.properties.org')" prop="org">
                  <select-organization :width="'100%'"
                                       :show-label="false"
                                       :clearable="false"
                                       style="margin-right: 30px;"
                                       v-model="instance.org"
                                       v-if="editing"/>
                  <p class="form-field-value" v-if="!editing">{{instance.orgName}}</p>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="8">
                <FormItem :label="$i18n.t('system.user.properties.username')" prop="username">
                  <Input type="text" v-model="instance.username" :maxlength="16" v-if="editing"/>
                  <p class="form-field-value" v-if="!editing">{{instance.username}}</p>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem :label="$i18n.t('system.user.properties.name')" prop="name">
                  <Input type="text" v-model="instance.name" :maxlength="64" v-if="editing" />
                  <p class="form-field-value" v-if="!editing">{{instance.name}}</p>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="8">
                <FormItem :label="$i18n.t('system.user.properties.birthday')" prop="birthday">
                  <DatePicker type="date" v-model="instance.birthday" :editable="false"  v-if="editing" />
                  <p class="form-field-value" v-if="!editing">{{instance.birthday}}</p>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem :label="$i18n.t('system.user.properties.email')" prop="email">
                  <Input type="text" v-model="instance.email" suffix="ios-mail" :maxlength="512"  v-if="editing"/>
                  <p class="form-field-value" v-if="!editing">{{instance.email}}</p>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="8">
                <FormItem :label="$i18n.t('system.user.properties.qq')" prop="qq">
                  <Input type="text" v-model="instance.qq" :maxlength="16"  v-if="editing" />
                  <p class="form-field-value" v-if="!editing">{{instance.qq}}</p>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem :label="$i18n.t('system.user.properties.wechat')" prop="wechat">
                  <Input type="text" v-model="instance.wechat" suffix="ios-chatbubbles-outline" :maxlength="32"  v-if="editing" />
                  <p class="form-field-value" v-if="!editing">{{instance.wechat}}</p>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="8">
                <FormItem :label="$i18n.t('system.user.properties.cellphone')" prop="cellphone">
                  <Input type="text" v-model="instance.cellphone" suffix="ios-phone-portrait" :maxlength="16"  v-if="editing" />
                  <p class="form-field-value" v-if="!editing">{{instance.cellphone}}</p>
                </FormItem>
              </Col>
              <Col span="8">
                <FormItem :label="$i18n.t('system.user.properties.telephone')" prop="telephone">
                  <Input type="text" v-model="instance.telephone" suffix="ios-call" :maxlength="16"  v-if="editing"/>
                  <p class="form-field-value" v-if="!editing">{{instance.telephone}}</p>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="16">
                <FormItem :label="$i18n.t('system.user.properties.remark')" prop="remark">
                  <Input type="textarea" :rows="3" v-model="instance.remark" :maxlength="512"  v-if="editing"/>
                  <p class="form-field-value" v-if="!editing">{{instance.remark}}</p>
                </FormItem>
              </Col>
            </Row>
          </Form>
        </Col>
      </Row>
    </div>
  </div>
</template>
<script>
import form from '../../../utils/form';
import api from '../../../apis/modules/system/user';
import attachmentApi from '../../../apis/attachments';
import VueCoreImageUpload from 'vue-core-image-upload';
import SelectOrganization from '../../../components/select-organization';

export default {
  name: 'user-details',
  components: {
    VueCoreImageUpload,
    SelectOrganization
  },
  data () {
    const __exists = (rule, value, callback) => {
      const message = this.$i18n.t('system.user.errors.username_repeat').toString();
      this.$http.get(api.exist, {username: this.instance.username, ignore: this.instance.id})
        .then(res => res.exist ? callback(new Error(message)) : callback());
    };
    return {
      status: form.STATUS_VIEW,
      avatar: null,
      submitting: false,
      instance: {
        id: this.$route.params.id
      },
      baseInformationRules: {
        org: [
          {required: true, trigger: 'blur', message: this.$i18n.t('errors.field_not_empty')}
        ],
        name: [
          {required: true, trigger: 'blur', message: this.$i18n.t('errors.field_not_empty')}
        ],
        username: [
          {required: true, trigger: 'blur', message: this.$i18n.t('errors.field_not_empty')},
          {validator: __exists, trigger: 'blur'}
        ],
        email: [
          {type: 'email', trigger: 'blur', message: this.$i18n.t('system.user.errors.invalid_email')}
        ],
        cellphone: [
          {pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, trigger: 'blur', message: this.$i18n.t('system.user.errors.invalid_cellphone')}
        ],
        telephone: [
          {pattern: /^(0\\d{2,3}-\\d{7,8}(-\\d{3,5}){0,1})|(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})$/, trigger: 'blur', message: this.$i18n.t('system.user.errors.invalid_telephone')}
        ]
      }
    };
  },
  computed: {
    uploadApi: function () {
      return attachmentApi.upload(this.instance.id);
    },
    avatarSrc: function () {
      return attachmentApi.download(this.avatar);
    },
    editing: function () {
      return form.isEditing(this.status);
    }
  },
  methods: {
    get (id) {
      this.$http.get(api.get(id)).then(res => {
        this.instance = res;
        this.avatar = res.avatar;
      });
    },
    init () {
      if (this.instance.id === '-1') {
        this.status = form.STATUS_CREATE;
        this.$http.get(api.newId).then(res => (this.instance.id = res.newId));
      } else {
        this.get(this.instance.id);
      }
    },
    edit () {
      this.status = form.STATUS_MODIFY;
    },
    submit () {
      this.submitting = true;
      this.$refs['basicInformation'].validate().then(isValid => {
        if (isValid) {
          if (this.$commons.isBlank(this.instance.birthday)) {
            delete this.instance.birthday;
          }
          const action = form.isCreating(this.status) ? this.$http.post : this.$http.put;
          action(api.common, this.instance).then(res => {
            this.instance = res;
            this.status = form.STATUS_VIEW;
            this.submitting = false;
            this.$Notice.success({
              title: this.$i18n.t('common.title.prompt'),
              desc: this.$i18n.t('common.message.save_success')
            });
          }).catch(() => (this.submitting = false));
        } else {
          this.submitting = false;
        }
      });
    },
    imageuploaded (res) {
      this.avatar = res.id;
      this.instance.avatar = this.avatar;
    }
  },
  created () {
    this.init();
  }
};
</script>
<style scoped>
  .container-avatar {
    width: 120px;
    height: 120px;
    margin: 0 auto;
  }

  .div-avatar {
    display: block;
    text-align: center;
    padding-top: 25px;
    font-size: 14px;
    color: #0bb1df;
    background-color: #f8f8f9;
    width: 120px;
    height: 120px;
  }

  .image-avatar {
    height: 120px;
    width: 120px;
    border: 1px solid #f8f8f9;
  }
</style>
