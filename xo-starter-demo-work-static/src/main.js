import Vue from 'vue'
import ElementUI from 'element-ui'
import App from './App'
import router from './router'
import store from './store'
import axios from 'axios'

import 'normalize.css/normalize.css'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css
import '@/icons'
import '@/permission'
import directives from "./directive"

import {getCookies, getToken} from '@/bin/utils/auth'
import {getEnumList} from '@/bin/api/globals'

Vue.prototype.$http = axios
Vue.config.productionTip = false

Vue.use(directives);
Vue.use(ElementUI);

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
  mounted() {
    this.initEnums();
  },
  methods: {
    initEnums() {
      Vue.prototype.$enums = {};  //先初始化,避免undefined错误
      initEnumList();

      function initEnumList() {
        getEnumList().then((res) => {
          //res.data: [{name: "EnabledStatus", des: "EnabledStatus", items: {0: "停用", 1: "启用"}},…]
          //转化为:{EnabledStatus: [{text: "停用", value:"0"}, {text: "启用", value:"1"}]}
          let enums = {};
          res.data.forEach((it) => {
            enums[it.name] = [];
            for (let key in it.items) {
              enums[it.name].push({text: it.items[key], value: key});
            }
          });
          Vue.prototype.$enums = enums;
        }).catch(() => setTimeout(initEnumList, 1000 * 30));
      }
    },
  },
});
