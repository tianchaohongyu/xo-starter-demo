<template>
  <div class="add-identity all-dialog" v-if="dialogVisible">
    <el-dialog title="新增身份" :visible.sync="dialogVisible" :close-on-click-modal="false" width="650px">
      <div class="add-identity-dialog-con">
        <el-form
          :model="addIdentity"
          :rules="rules"
          ref="addIdentity"
          label-width="100px"
          class="ruleForm user-form clearfix"
        >
          <div class="user-info clearfix">
            <h4>身份信息</h4>
            <div class="left user-info-option">
              <el-form-item label="名称：" prop="name">
                <el-input v-model="addIdentity.name" size="small" placeholder="请输入名称"></el-input>
              </el-form-item>
            </div>
            <div class="left user-info-option">
              <el-form-item label="代码：" prop="code">
                <el-input v-model="addIdentity.code" size="small" placeholder="请输入名称"></el-input>
              </el-form-item>
            </div>

            <div class="left user-info-option">
              <el-form-item label="身份类型：" prop="type">
                <el-select filterable v-model="addIdentity.type" style="width:184px;" size="small" placeholder="请选择">
                  <el-option
                    v-for="(item, index) in typeList"
                    :key="index"
                    :label="item.text"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </div>
          </div>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="resetForm('addIdentity')">取 消</el-button>
        <el-button size="small" type="primary" @click="submitForm('addIdentity')">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {addIdentity} from "@/bin/api/identitys";
  import {checkOrdinal} from '@/bin/utils/validate'

  export default {
  name: "addIdentity",
  data() {
    return {
      // 窗口显示隐藏
      dialogVisible: false,
      typeList:[],
      // 传值给后台

      addIdentity: {
        name: "",
        code: "",
        type: "",
      },
      // 输入框验证
      rules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        code: [{ required: true, message: "请输入代码", trigger: "blur" }],
        type: [{ required: true, message: "请选择类型", trigger: "blur" }],
        ordinal: [
          { validator: checkOrdinal, trigger: 'blur', required: true }
        ]
      }
    };
  },
  methods: {
    // 取消重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false;
    },
    show() {
      this.dialogVisible = true;
      this.typeList = this.$enums.IdentityType;
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          addIdentity(this.addIdentity).then(res => {
            this.resetForm(formName);
            this.$message({
              type: "success",
              message: "保存成功!",
              center: true
            });
            this.$emit("refreshAddList");
          });
        } else {
          console.log("请将 * 的内容填完!!");
          return false;
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.line{
  height: 1px;
  background: #f0f0f0;
}
</style>


