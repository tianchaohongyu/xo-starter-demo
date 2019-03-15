<!--
-- 头像标签
-- className 类名
-- imgUrl 图片链接
-- text   显示文本
-- info 显示描述
-- href 点击跳转链接
-- click 单击事件
-->

<template>
  <div :class="classes" @click="click">
    <img class="head-img" :src="imgUrl" />
    <div :class="{'head-text-one': !info, 'head-text-two': info}">
      <a :href="href">
        <div :class="{'head-activate': href}">{{ text }}</div>
        <div class="head-info" v-if="info">{{ info }}</div>
      </a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HeadImg',
  props: {
    className: {
      type: String,
      default: 'head-img-div',
    },
    imgUrl: {
      type: String,
      require: true,
    },
    text: {
      type: String,
      require: true,
    },
    info: {
      type: String,
    },
    href: {
      type: String,
    },

  },
  computed: {
    classes() {
      return this.className
    },
  },
  methods:{
    click(event){
      this.$emit('click',event)
    }
  }
}
</script>

<style scoped>
.head-img-div {
  height: 48px;
  margin: 5px 0px;
}

.head-img-div .head-img{
  height: 100%;
  float: left;
  border-radius: 50px;
  margin-right: 10px;
}

.head-img-div .head-text-one{
  line-height: 48px;
}

.head-img-div .head-text-two div{
  line-height: 24px;
  height: 24px;
}

.head-img-div .head-text-two .head-info{
  color: gray;
}

.head-img-div .head-activate{
  color: #42a4ff;
}


</style>
