<template>
  <div id="visitors">
    <div class="visitors-header clearfix">
      <div class="visitors-header-left left">
        <el-button type="primary" size="mini" @click="addDialogShow">新增</el-button>
      </div>
      <div class="visitors-header-right right">
        <el-input placeholder="请输入内容" v-model="searchVal" class="input-with-select" size="small">
          <el-button slot="append" icon="el-icon-search" size="small" @click="searchList"></el-button>
        </el-input>
      </div>
    </div>
    <div class="visitors-body">
      <el-table
        :data="visitorList"
        border
        size="medium"
        height="100%"
        style="width: 100%">
        <el-table-column
          label="用户"
          width="220">
          <template slot-scope="scope">
            <head-img
              :img-url="scope.row.imgUrl"
              :text="scope.row.nickName"
              :info="scope.row.mobile"
              href="JavaScript:;"
              @click="editDialogShow(scope.row)">
            </head-img>
          </template>
        </el-table-column>

        <el-table-column
          prop="identity.name"
          label="身份"
          width="100">
        </el-table-column>

        <el-table-column
          prop="status.text"
          label="状态"
          width="100">
        </el-table-column>

        <el-table-column
          prop="createTime"
          label="创建时间"
          width="160">
        </el-table-column>

        <el-table-column
          prop="updateTime"
          label="更新时间"
          width="160">
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="editDialogShow(scope.row)">编辑</el-button>
            <el-button type="text" size="small" v-if="scope.row.status.text == '启用'" @click="blockUp(scope.row.id)">停用</el-button>
            <el-button type="text" size="small" v-else="scope.row.status.text == '停用'" @click="startUp(scope.row.id)">启用</el-button>
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

    <!-- 新增用户 -->
    <add-visitor ref='addDialog'  @refreshAddList='initData'/>
    <!-- 编辑用户 -->
    <edit-visitor ref='editDialog' @refreshList='initData'/>
  </div>
</template>
<script>
  import AddVisitor from './components/addVisitor'
  import EditVisitor from './components/editVisitor'
  import HeadImg from '@/components/HeadImg/index'
  import {disableVisitor, enableVisitor, getVisitorList,} from '@/bin/api/visitors'
  import {changeTime} from '@/bin/utils/index'

  export default {
  name: "Users",
  components:{
    HeadImg,
    AddVisitor,
    EditVisitor,
  },
  data() {
    return {
      // 列表数据
      visitorList: [],
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
      actorid:''
    }
  },
  mounted(){
    // 初始化数据
    this.initData();
  },
  methods: {
    initData(){
      getVisitorList (this.params).then(res => {
        let data = res.data;
        // 设置分页数据
        this.pageNum = data.number;
        this.pageSize = data.size;
        this.totalNum = data.count;
        // 处理列表数据
        this.visitorList = data.contents;
        data.contents.forEach((item,index) => {
          this.visitorList[index].createTime = changeTime(item.createTime);
          this.visitorList[index].updateTime = changeTime(item.updateTime);
        });
      }).catch(err => {
        console.log(err)
      })
    },
    // 停用
    blockUp(id) {
      this.$confirm('您确定要停用该用户吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        disableVisitor(id).then(res => {
          this.$message({
            type: 'success',
            message: '停用用户成功!',
            center: true
          });
          this.initData();
        }).catch(err => {
          return false
        });
      });
    },

    // 启用
    startUp(id) {
      this.$confirm('您确定要启用该用户吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enableVisitor(id).then(res => {
          this.$message({
            type: 'success',
            message: '启用用户成功!',
            center: true
          });
          this.initData();
        }).catch(err => {
          return false
        });
      });

    },

    addDialogShow(){
      this.$refs.addDialog.show()
    },
    editDialogShow(row){
      this.$refs.editDialog.show(row)
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
#visitors {
  padding: 20px;
  height: calc(100vh - 84px);
  p {
  margin: 0;
    div {
      margin-bottom:0;
    }
  }

  .visitors-body {
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


