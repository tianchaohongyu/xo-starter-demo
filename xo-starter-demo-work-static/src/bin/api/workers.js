import request from '@/bin/utils/request'


// 查询工作人员列表
export function getWorkerList({pageNum, pageSize, orderBy, sort, keyword} = data) {
  return request({
    url: '/workers?pageNum=' + pageNum + '&pageSize=' + pageSize + '&orderBy=' + orderBy + '&sort=' + sort + '&keyword=' + keyword,
    method: 'GET'
  })
}

// 新增工作人员
export function addWorker({actorName, name, organId, roleId, username, ordinal} = data) {
  return request({
    url: '/workers',
    method: 'POST',
    data: {
      actorName,
      name,
      organId,
      roleId,
      username,
      ordinal
    }
  })
}

// 更新工作人员
export function updateWorker({id, name, ordinal, username} = data) {
  return request({
    url: '/workers',
    method: 'PUT',
    data: {
      id,
      name,
      ordinal,
      username
    }
  })
}

// 停用工作人员
export function disableWorker(id) {
  return request({
    url: '/workers/disable/' + id,
    method: 'PATCH'
  })
}

// 启用工作人员
export function enableWorker(id) {
  return request({
    url: '/workers/enable/' + id,
    method: 'PATCH'
  })
}

// 重置密码
export function resetPwd({id, managePassword} = data) {
  return request({
    url: '/workers/reset/' + id + '?managePassword=' + managePassword,
    method: 'PATCH'
  })
}

// 获取工作人员信息
export function getWorkerInfo(id) {
  return request({
    url: '/workers/' + id,
    method: 'GET'
  })
}

// 查询职务列表
export function getActorList(userId) {
  return request({
    url: '/workers/' + userId + '/actors',
    method: 'GET',
  })
}

// 设置默认职务
export function setDefaultActor(id) {
  return request({
    url: '/workers/default-actor/' + id,
    method: 'PATCH'
  })
}

// 新增职务
export function addActor({userId, name, organId, roleId} = data) {
  return request({
    url: '/workers/' + userId + '/actors',
    method: 'POST',
    data: {
      name,
      organId,
      roleId
    }
  })
}

// 更新职务
export function updateActor({userId, id, name, organId, roleId} = data) {
  return request({
    url: '/workers/' + userId + '/actors',
    method: 'PUT',
    data: {
      id,
      name,
      organId,
      roleId
    }
  })
}

// 删除职务
export function delActor({userId, id} = data) {
  return request({
    url: '/workers/' + userId + '/actors/' + id,
    method: 'DELETE'
  })
}
