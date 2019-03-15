<template>
    <div class="edit-worker all-dialog" v-if="editIdentityVisible">
      <el-dialog
        title="编辑身份"
        :visible.sync="editIdentityVisible"
        :close-on-click-modal=false
        width="35%"
        >
        <div class="edit-worker-dialog-con">
          <el-form :model="editIdentity" :rules="rules" label-position="left" ref="editIdentity" label-width="100px" class="ruleForm user-form clearfix">

            <div class="left user-info-option">
              <el-form-item label="名称：" prop="name">
                <el-input v-model="editIdentity.name" size="small" placeholder="请输入名称"></el-input>
              </el-form-item>
            </div>

            <div class="left user-info-option">
              <el-form-item label="代码：" prop="code">
                <el-input v-model="editIdentity.code" size="small" placeholder="请输入名称"></el-input>
              </el-form-item>
            </div>

            <div class="left user-info-option">
              <el-form-item label="身份类型：" prop="type">
                <el-select filterable v-model="editIdentity.type" style="width:184px;" size="small" placeholder="请选择">
                  <el-option
                    v-for="(item, index) in typeList"
                    :key="index"
                    :label="item.text"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </div>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="resetForm('editIdentity')">取 消</el-button>
          <el-button size="small" type="primary" @click="submitForm('editIdentity')">保 存</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
  import {updateIdentity} from '@/bin/api/identitys'
  import {checkOrdinal} from '@/bin/utils/validate'

  export default {
  name:"editIdentity",
  data(){
    return {
      editIdentityVisible: false,
      typeList:[],
      rules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        code: [{ required: true, message: "请输入代码", trigger: "blur" }],
        type: [{ required: true, message: "请选择类型", trigger: "blur" }],
      },
      editIdentity: {
        id:'',
        name: "",
        code: "",
        type: "",
      },
    }
  },
  methods:{
    show(data){
      this.editIdentityVisible = true;
      this.typeList = this.$enums.IdentityType;
      this.editIdentity.id = data.id;
      this.editIdentity.name = data.name;
      this.editIdentity.code = data.code;
      this.editIdentity.type = data.type.value;
    },
    resetForm(formName) {
      this.editIdentityVisible = false
      this.$refs[formName].resetFields();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateIdentity(this.editIdentity).then(res => {
            this.$message({
              type: "success",
              message: "保存成功!",
              center: true
            });
            this.$emit('refreshList')
            this.editIdentityVisible = false
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


