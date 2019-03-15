import axios from 'axios'
import {Message} from 'element-ui'
import {getToken, removeCookies, removeToken, setToken} from '@/bin/utils/auth'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 3000 * 10 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    const token = getToken()
    if (token) {
      config.headers['x-auth-token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  error => {
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    const token = getToken()
    if (token === undefined || token === 'undefined') {
      setToken(response.headers['x-auth-token'], 'token')
    }
    return response
  }, error => {
    // 后台无响应
    if(error.response === undefined || error.response === 'undefined') {
      Message({
        message: '连接服务器失败，请稍后再试。',
        type: 'warning',
        duration: 3 * 1000,
        center:true
      })
      return false
    }

    // 401 错误
    if (error.response.status === 401) {
      // 登录时工作人员名或密码错误，不刷新浏览器
      if(error.response.data.code === 'E980') {
        Message({
          message: error.response.data.msg,
          type: 'warning',
          duration: 3 * 1000,
          center:true
        })
        return false
      }
      // 登录授权过期
      Message({
        message: error.response.data.msg,
        type: 'warning',
        duration: 2 * 1000,
        center:true,
        onClose: function() {
          removeToken('token')
          removeCookies('userInfo')
          removeCookies('privilegs')
          removeCookies('sidebarStatus')
          location.reload()
        }
      })
      return Promise.reject(error)
    }

    // 后台有相应，其他错误
    if (error.response) {
      Message({
        message: error.response.data.msg,
        type: 'error',
        duration: 3 * 1000,
        center:true
      })
    }

    return Promise.reject(error)
  }
)

export default service
