<template>
  <div class="main-container">
    <div class="main-content">
      <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
          <div class="login-container">
            <div class="center">
              <h1>
                <i class="ace-icon fa fa-leaf green"></i>
                <span class="">控台登录</span>
              </h1>
            </div>

            <div class="space-6"></div>

            <div class="position-relative">
              <div id="login-box" class="login-box visible widget-box no-border">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header blue lighter bigger">
                      <i class="ace-icon fa fa-coffee green"></i>
                      请输入用户名密码
                    </h4>

                    <div class="space-6"></div>

                    <form>
                      <fieldset>
                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <input v-model="user.loginName" type="text" class="form-control" placeholder="用户名"/>
                            <i class="ace-icon fa fa-user"></i>
                          </span>
                        </label>

                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <input v-model="user.password" type="password" class="form-control" placeholder="密码"/>
                            <i class="ace-icon fa fa-lock"></i>
                          </span>
                        </label>
                    <!--验证码-->
                   <!--     <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <div class="input-group">
                              <input v-model="user.imageCode" type="text" class="form-control" placeholder="验证码">
                              <span class="input-group-addon" id="basic-addon2">
                                <img v-on:click="loadImageCode()" id="image-code" alt="验证码"/>
                              </span>
                            </div>
                          </span>
                        </label>-->

                        <div class="space"></div>

                        <div class="clearfix">
                          <label class="inline">
                            <input v-model="remember" type="checkbox" class="ace"/>
                            <span class="lbl">记住我</span>
                          </label>

                          <button type="button"
                                  class="width-35 pull-right btn btn-sm btn-primary"
                                  v-on:click="login()">
                            <i class="ace-icon fa fa-key"></i>
                            <span class="bigger-110">登录</span>
                          </button>
                        </div>

                        <div class="space-4"></div>
                      </fieldset>
                    </form>

                  </div><!-- /.widget-main -->

                </div><!-- /.widget-body -->
              </div><!-- /.login-box -->
            </div><!-- /.position-relative -->

          </div>
        </div><!-- /.col -->
      </div><!-- /.row -->
    </div><!-- /.main-content -->
  </div><!-- /.main-container -->
</template>

<script>
  export default {
    name: "login",
    data: function() {
      return {
        user: {},
        // remember: true, // 默认勾选记住我
        // imageCodeToken: ""
      }
    },
    // mounted: function() {
    //   let _this = this;
    //   $("body").removeClass("no-skin");
    //   $("body").attr("class", "login-layout light-login");
    //   // console.log("login");
    //
    //   // 从缓存中获取记住的用户名密码，如果获取不到，说明上一次没有勾选“记住我”
    //   let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
    //   if (rememberUser) {
    //     _this.user = rememberUser;
    //   }
    //
    //   // 初始时加载一次验证码图片
    //   _this.loadImageCode();
    // },
    methods: {
      login () {
        let _this = this;
        // 将明文存储到缓存中
        // let passwordShow = _this.user.password;
        // 如果密码是从缓存带出来的，则不需要重新加密
        let md5 = hex_md5(_this.user.password);
        // let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
        _this.user.password = hex_md5(_this.user.password + KEY);
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login', _this.user).then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            console.log("登录成功：", resp.content);
            //保存登录会话
            // SessionStorage.set("USER",resp.content)
            let loginUser = resp.content;
            //通用方法保存会话信息, 方法在tool.js里
            Tool.setLoginUser(resp.content);
            _this.$router.push("/welcome")
          } else {
            Toast.warning(resp.message);
            _this.user.password = "";
            // _this.loadImageCode();
          }
        });
      },

      /**
       * 加载图形验证码
       */
      // loadImageCode: function () {
      //   let _this = this;
      //   _this.imageCodeToken = Tool.uuid(8);
      //   $('#image-code').attr('src', process.env.VUE_APP_SERVER + '/system/admin/kaptcha/image-code/' + _this.imageCodeToken);
      // },
    }
  }
</script>

<style scoped>
  .input-group-addon {
    padding: 0;
  }
</style>
