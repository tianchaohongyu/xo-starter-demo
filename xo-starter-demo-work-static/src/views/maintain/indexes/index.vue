<template>
  <div id="indexes" style="padding:20px;">
    <div class="indexes-header">
      <el-button type="primary" size="mini" @click="reBuild">重建全文索引</el-button>

      <div class="visitors-header-right right">
        <el-input placeholder="过滤内容" v-model="searchVal" class="input-with-select" size="small">
          <el-button slot="append" icon="el-icon-search" size="small" @click="filter"></el-button>
        </el-input>
      </div>
    </div>
    <div class="indexes-body">
      <el-table
        ref="multipleTable"
        :data="indexList"
        tooltip-effect="dark"
        style="width: 100%"
        border
        :default-sort = "{prop: 'simpleName', order: 'ascending'}"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>

        <el-table-column
          sortable
          prop="simpleName"
          label="实体类名"
          width="150">
          >
        </el-table-column>

        <el-table-column
          sortable
          prop="name"
          label="全名"
          >
        </el-table-column>

      </el-table>
    </div>
  </div>
</template>

<script>
  import {getIndexList, rebuildIndex} from '@/bin/api/indexs'

  export default {
  name:'Indexes',
  data() {
    return {
      indexList: [],                // 列表数据
      vo:[],
      multipleSelection: [],        // 复选框选中数据
      rebuildData: [],               // 重建索引数据
      searchVal: '',
    }
  },
  mounted() {
    this.initData()
  },
  methods: {

    // 初始化获取数据
    initData() {
      getIndexList().then(res => {
        let data = res.data;
        this.vo = data.map((it,index) => {
          return {name: it, simpleName: it.split('.').pop(), indexName: it.toLowerCase()};
        });
        this.indexList = this.vo;
      }).catch(err => {
        console.log(err)
      })
    },

    filter(){
      let value = this.searchVal.toLowerCase();
      this.indexList = this.vo.filter((it) => it.indexName.indexOf(value) != -1);
    },

    // 复选框选择数据
    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    // 重建索引
    reBuild() {
      if(!this.multipleSelection.length) {
        this.$message({
          message: '请选择要操作的记录!',
          type: 'info',
          center:true
        });
      } else {
        this.multipleSelection.map((item,index) => {
          this.rebuildData.push(item.name)
        })

        this.$confirm('您确定要重建这些全文索引吗?', '确认提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          rebuildIndex(this.rebuildData).then(res => {
            this.$message({
              message: '重建全文索引成功',
              type: 'success',
              center:true,
            });
            this.$refs.multipleTable.clearSelection();
          }).catch(err => {
            this.$message({
              message: '重建全文索引失败',
              type: 'error',
              center:true
            })
          });
        }).catch(err => {

        })
      }
    },
  }
}
</script>

<style lang="scss" scoped>
  .indexes-body {
    margin-top: 20px;
  }
</style>




