<template>
  <div id="identitys">
    <div class="identitys-header clearfix">
      <div class="identitys-header-left left">
        <el-button type="primary" size="mini" @click="addDialogShow">新增</el-button>
      </div>
      <div class="identitys-header-right right">
        <el-input placeholder="请输入内容" v-model="searchVal" class="input-with-select" size="small">
          <el-button slot="append" icon="el-icon-search" size="small" @click="searchList"></el-button>
        </el-input>
      </div>
    </div>
    <div class="identitys-body">
      <el-table
        :data="identityList"
        border
        size="medium"
        height="100%"
        style="width: 100%">

        <el-table-column
          prop="name"
          label="名称"
          width="100">
        </el-table-column>

        <el-table-column
          prop="code"
          label="代码"
          width="100">
        </el-table-column>

        <el-table-column
          prop="type.text"
          label="类型"
          width="100">
        </el-table-column>



        <el-table-column
          prop="createTime"
          label="创建时间"
          width="170">
        </el-table-column>

        <el-table-column
          prop="createUser"
          label="创建人"
          width="130">
        </el-table-column>

        <el-table-column
          prop="updateTime"
          label="修改时间"
          width="170">
        </el-table-column>

        <el-table-column
          prop="updateUser"
          label="修改人"
          width="130">
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="130">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="editDialogShow(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="deleteIt(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>

      </el-table>
    </div>

    <!-- pagination -->
    <div class="user-pagination">
      <div class="block">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[15, 20, 30, 50, 80, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalNum">
        </el-pagination>
      </div>
    </div>

    <!-- 新增身份 -->
    <add-identity ref='addDialog'  @refreshAddList='initData'/>
    <!-- 编辑身份 -->
    <edit-identity ref='editDialog' @refreshList='initData'/>

  </div>
</template>
<script>
  import AddIdentity from './components/addIdentity'
  import EdiIdentity from './components/editIdentity'
  import {deleteIdentity, getIdentityList} from '@/bin/api/identitys'
  import {changeTime} from '@/bin/utils/index'

  export default {
    name: "Identity",
    components:{
      AddIdentity: AddIdentity,
      EditIdentity: EdiIdentity,
    },
    data() {
      return {
        // 列表数据
        identityList: [],
        searchVal: '',    // 关键字
        // 参数
        params:{
          'keyword': '',
          'orderBy': '',
          'pageNum': 1,
          'pageSize': 15,
          'sort': ''
        },
        // 分页
        pageNum: 1,
        totalNum: 0,
        pageSize: 15,
        userName: '',
      }
    },
    mounted(){
      // 初始化数据
      this.initData();
    },
    methods: {
      initData(){
        getIdentityList (this.params).then(res => {
          let data = res.data
          // 设置分页数据
          this.pageNum = data.number
          this.pageSize = data.size
          this.totalNum = data.count
          // 处理列表数据
          this.identityList = data.contents
          data.contents.forEach((item,index) => {
            this.identityList[identity].createTime = changeTime(item.createTime)
            this.identityList[identity].updateTime = changeTime(item.updateTime)
          });
        }).catch(err => {
          console.log(err)
        })
      },

      addDialogShow(){
        this.$refs.addDialog.show()
      },
      editDialogShow(row){
        this.$refs.editDialog.show(row)
      },

      // 删除
      deleteIt(id) {
        this.$confirm('您确定要删除该身份吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteIdentity(id).then(res => {
            this.$message({
              type: 'success',
              message: '删除身份人员成功!',
              center: true
            });
            this.initData();
          }).catch(err => {
            return false
          });
        });
      },

      // 搜索
      searchList(){
        this.params.keyword = this.searchVal
        this.initData()
      },

      // 选择分页数量
      handleSizeChange(val) {
        this.params.pageSize = val
        this.initData()
      },

      // 跳转页数
      handleCurrentChange(val) {
        this.params.pageNum = val
        this.initData()
      },
    }
  };
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  #identitys {
    padding: 20px;
    height: calc(100vh - 84px);
    p {
      margin: 0;
      div {
        margin-bottom:0;
      }
    }

    .identitys-body {
      margin-top: 10px;
      height: 86.1%;
      overflow: auto
    }

    .user-pagination {
      position: absolute;
      left:0;
      bottom: 0px;
      z-index: 9;
      width: 100%;
      padding: 8px 0;
      background: #F9F9F9;
      .block {
        text-align: center;
      }
    }

    .tips {
      span {
        color: #d00;
      }
    }

  }

</style>


