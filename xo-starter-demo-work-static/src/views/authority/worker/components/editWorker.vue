<template>
    <div class="edit-worker all-dialog" v-if="editWorkerVisible">
      <el-dialog
        title="编辑工作人员"
        :visible.sync="editWorkerVisible"
        :close-on-click-modal=false
        width="35%"
        >
        <div class="edit-worker-dialog-con">
          <el-form :model="editWorker" :rules="rules" label-position="left" ref="editWorker" label-width="100px" class="ruleForm user-form clearfix">
            <div class="user-info clearfix">
                <div class="left user-info-option">
                  <el-form-item label="用户名：" prop="username">
                    <el-input v-model="editWorker.username" size="small" placeholder="请输入用户名"></el-input>
                  </el-form-item>
                </div>
                <div class="left user-info-option">
                  <el-form-item label="姓名：" prop="name">
                    <el-input v-model="editWorker.name" size="small" placeholder="请输入姓名"></el-input>
                  </el-form-item>
                </div>
                <div class="left user-info-option">
                  <el-form-item label="序号：" prop="ordinal">
                    <el-input v-model.number="editWorker.ordinal" size="small" placeholder="请输入序号"></el-input>
                  </el-form-item>
                </div>
            </div>
            </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="resetForm('editWorker')">取 消</el-button>
          <el-button size="small" type="primary" @click="submitForm('editWorker')">保 存</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
  import {updateWorker} from '@/bin/api/workers'
  import {checkOrdinal} from '@/bin/utils/validate'

  export default {
  name:"editWorker",
  data(){
    return {
      editWorkerVisible: false,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        ordinal: [
          { validator: checkOrdinal, trigger: 'blur', required: true }
        ]
      },
      editWorker: {
        id:'',
        name: '',
        ordinal: '',
        username: '',
      },
    }
  },
  methods:{
    show(data){
      this.editWorkerVisible = true
      this.editWorker.id = data.id;
      this.editWorker.name = data.name;
      this.editWorker.ordinal = data.ordinal;
      this.editWorker.username = data.username;
    },
    resetForm(formName) {
      this.editWorkerVisible = false
      this.$refs[formName].resetFields();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateWorker(this.editWorker).then(res => {
            this.$message({
              type: "success",
              message: "保存成功!",
              center: true
            });
            this.$emit('refreshList')
            this.editWorkerVisible = false
          })
        } else {
          return false;
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .edit-worker-dialog-con {
    margin-top: 20px;
  }
  .user-info-option {
    margin-right: 30px;
  }
</style>


