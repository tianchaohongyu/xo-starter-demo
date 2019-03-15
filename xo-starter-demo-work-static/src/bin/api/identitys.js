import request from '@/bin/utils/request'


/**
 * 查询身份列表
 * @param pageNum 分页号
 * @param pageSize 分页大小
 * @param orderBy 排序字段
 * @param sort 是否升序
 * @param keyword 搜索关键字
 */
export function getIdentityList({pageNum, pageSize, orderBy, sort, keyword} = data) {
  return request({
    url: `identitys`,
    method: 'GET',
    params: {pageNum, pageSize, orderBy, sort, keyword},
  })
}

/**
 * 新增身份
 * @param name 身份名称
 * @param code 身份代码
 * @param type 身份类型
 */
export function addIdentity({name, code,  type} = data) {
  return request({
    url: '/identitys',
    method: 'POST',
    data: {name, code,  type},
  })
}

/**
 * 更新身份
 * @param id 身份id
 * @param name 身份名称
 * @param code 身份代码
 * @param type 身份类型
 */
export function updateIdentity({id, name, code, type} = data) {
  return request({
    url: '/identitys',
    method: 'PUT',
    data: {id, name, code, type},
  })
}

/**
 * 删除身份
 * @param id 身份id
 */
export function deleteIdentity(id) {
  return request({
    url: `/identitys/${id}`,
    method: 'DELETE',
    data: {},
  })
}
