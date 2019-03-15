import request from '@/bin/utils/request'


/**
 * 查询枚举列表
 */
export function getEnumList() {
  return request({
    url: `enums`,
    method: 'GET'
  })
}
