<template>
  <div class="add-visitor all-dialog" v-if="dialogVisible">
    <el-dialog title="新增用户" :visible.sync="dialogVisible" :close-on-click-modal="false" width="650px">
      <div class="add-visitor-dialog-con">
        <el-form
          :model="dto"
          :rules="rules"
          ref="addVisitor"
          label-width="100px"
          class="ruleForm user-form clearfix"
        >
          <div class="user-info clearfix">
            <h4>用户信息</h4>

            <div class="left user-info-option">
              <el-form-item label="用户昵称：" prop="nickName">
                <el-input v-model="dto.nickName" size="small" placeholder="请输入用户昵称"></el-input>
              </el-form-item>
            </div>

            <div class="left user-info-option">
              <el-form-item label="选择身份：" prop="identityId">
                <el-select
                  v-model="dto.identityId"
                  filterable
                  remote
                  placeholder="请输入关键词"
                  :remote-method="searchIdentity"
                  style="width:184px;"
                  size="small"
                  :loading="false">
                  <el-option
                    v-for="(item, index) in identityList"
                    :key="index"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </div>

            <div class="left user-info-option">
              <el-form-item label="手机：" prop="mobile">
                <el-input v-model="dto.mobile" size="small" placeholder="请输入手机"></el-input>
              </el-form-item>
            </div>

            <div class="clearfix"></div>

            <div class="left user-info-option">
              <el-form-item label="密码：" prop="password">
                <el-input v-model="dto.password" size="small" placeholder="请输入密码"></el-input>
              </el-form-item>
            </div>

            <div class="left user-info-option">
              <el-form-item label="安全密码：" prop="safePassword">
                <el-input v-model="dto.safePassword" size="small" placeholder="请输入安全密码"></el-input>
              </el-form-item>
            </div>

          </div>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="resetForm('addVisitor')">取 消</el-button>
        <el-button size="small" type="primary" @click="submitForm('addVisitor')">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {addVisitor} from "@/bin/api/visitors";
  import {getIdentityList} from "@/bin/api/identitys";
  import {checkOrdinal} from '@/bin/utils/validate'

  export default {
  name: "addVisitor",
  data() {
    return {
      // 窗口显示隐藏
      dialogVisible: false,
      // 传值给后台
      identityList:[],

      dto: {
        nickName: "",
        imgUrl: "",
        mobile: "",
        password: "",
        safePassword: "",
        identityId: "",
      },

      // 输入框验证
      rules: {
        nickName: [{ required: true, message: "请输入用户昵称", trigger: "blur" }],
        identityId: [{ required: true, message: "请选择身份", trigger: "blur" }],
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
      this.searchIdentity('');
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          addVisitor(this.dto).then(res => {
            this.dialogVisible = false;
            this.$refs[formName].resetFields();
            this.$message({
              type: "success",
              message: "保存成功!",
              center: true
            });
            setTimeout(() => this.$emit("refreshAddList"),1000);  //1秒后刷新
          });
        } else {
          console.log("请将 * 的内容填完!!");
          return false;
        }
      });
    },
    searchIdentity(keyword){
      getIdentityList({
        pageNum: 1,
        pageSize: 20,
        orderBy: '',
        sort: '',
        keyword: keyword,
      }).then(res => {
        this.identityList = res.data.contents;
      }).catch(err => {
        console.log(err);
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


