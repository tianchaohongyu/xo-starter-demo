import request from '@/bin/utils/request'


/**
 * 查询用户id
 * @param pageNum 分页号
 * @param pageSize 分页大小
 * @param orderBy 排序字段
 * @param sort    升序模式
 * @param keyword 关键字
 * @param status 状态
 */
export function getVisitorList({pageNum, pageSize, orderBy, sort, keyword, status, identityId} = data) {
  return request({
    url: '/visitors',
    method: 'GET',
    params:{pageNum, pageSize, orderBy, sort, keyword, status, identityId, },
  })
}

/**
 * 新增用户
 * @param nickName 昵称
 * @param imgUrl 头像
 * @param mobile 手机
 * @param password 密码
 * @param safePassword 安全密码
 * @param identityId 身份id
 */
export function addVisitor({nickName, imgUrl, mobile, password,safePassword, identityId} = data) {
  return request({
    url: '/visitors',
    method: 'POST',
    data: {nickName, imgUrl :imgUrl || null, mobile: mobile || null, password : password || null,safePassword:safePassword || null, identityId}
  })
}

/**
 * 更新用户
 * @param id 用户id
 * @param nickName 昵称
 * @param imgUrl 图片链接
 * @param mobile 手机
 * @param password 密码
 * @param safePassword 安全密码
 * @param identity 身份
 */
export function updateVisitor({id, nickName, imgUrl, mobile, password,safePassword, identityId} = data) {
  return request({
    url: '/visitors',
    method: 'PUT',
    data: {id, nickName, imgUrl :imgUrl || null, mobile: mobile || null, password : password || null,safePassword:safePassword || null, identityId}
  })
}

/**
 * 停用用户
 * @param id 用户id
 */
export function disableVisitor(id) {
  return request({
    url: `/visitors/disable/${id}`,
    method: 'PATCH'
  })
}

/**
 * 启用用户
 * @param id 用户id
 */
export function enableVisitor(id) {
  return request({
    url: `/visitors/enable/${id}`,
    method: 'PATCH'
  })
}
