<template>
  <Dropdown trigger="click" style="height: 32px; line-height: 32px;" placement="bottom-end">
    <Badge :count="100" overflow-count="99">
      <Avatar icon="ios-person" :src="avatar"/>
    </Badge>
    <DropdownMenu slot="list">
      <DropdownItem>You have a new message</DropdownItem>
    </DropdownMenu>
  </Dropdown>
</template>

<script>
import api from '../../apis/attachments';

export default {
  name: 'commons-dropdown-message',
  data () {
    return {
      avatar: this.$commons.isBlank(this.$commons.getUser().avatar) ? '' : api.download(this.$commons.getUser().avatar)
    };
  }
};
</script>
