<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{ course.name }}</router-link>
      ：
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/chapter" class="pink"> {{ chapter.name }}</router-link>
    </h4>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>标题</th>
        <th>VOD</th>
        <th>时长</th>
        <th>收费</th>
        <th>顺序</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="section in sections">
        <td>{{ section.id }}</td>
        <td>{{ section.title }}</td>
        <td>{{ section.vod }}</td>
        <td>{{ section.time | formatSecond }}</td>
        <td>{{ SECTION_CHARGE | optionKV(section.charge) }}</td>
        <td>{{ section.sort }}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(section)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button v-on:click="del(section.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>
          <div class="hidden-md hidden-lg">
            <div class="inline pos-rel">
              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown"
                      data-position="auto">
                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
              </button>

              <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                <li>
                  <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                                                    <span class="blue">
                                                                        <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                                    </span>
                  </a>
                </li>
                <li>
                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="green">
                                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                    </span>
                  </a>
                </li>

                <li>
                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                                                    <span class="red">
                                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                                    </span>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <!--模态框-->
    <div id="forn-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">标题</label>
                <div class="col-sm-10">
                  <input v-model="section.title" type="text" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ course.name }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">大章</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ chapter.name }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">视频</label>
                <div class="col-sm-10">
                  <file v-bind:input-id="'video-upload'"
                        v-bind:text="'上传视频'"
                        v-bind:suffixs="['mp4']"
                        v-bind:use="FILE_USE.COURSE.key"
                        v-bind:after-upload="afterUpload">
                  </file>
                  <!--视频显示-->
                  <div v-show="section.video" class="row">
                    <div class="col-md-9">
                      <video v-bind:src="section.video" controls="controls" id="video"></video>
                    </div>
                  </div>
                  <!--原本的代码-->
                  <!--<input v-model="section.video" type="text" class="form-control">-->
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">时长</label>
                <div class="col-sm-10">
                  <input v-model="section.time" type="text" class="form-control">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">收费</label>
                <div class="col-sm-10">
                  <select v-model="section.charge" class="form-control">
                    <option v-for="o in SECTION_CHARGE" v-bind:value="o.key">{{ o.value }}</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input v-model="section.sort" type="text" class="form-control">
                </div>
              </div>
            </form>

          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" v-on:click="save()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

  </div>
</template>

<script>
//引入外部文件一
import Pagination from "../../components/pagination"
import File from "../../components/file";

export default {
  //引入外部文件二
  components: {Pagination, File},
  name: "business-section",
  data: function () {
    //数据绑定写在这里
    return {
      section: {},
      sections: [],
      SECTION_CHARGE: SECTION_CHARGE,
      course: {},
      chapter: {},
      //视频的相关参数
      FILE_USE: FILE_USE,
    }
  },
  mounted: function () {
    let _this = this;
    _this.$refs.pagination.size = 5;
    let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
    let chapter = SessionStorage.get(SESSION_KEY_COURSE) || {};
    if (Tool.isEmpty(course) || Tool.isEmpty(chapter)) {
      _this.$router.push("/welcome");
    }
    _this.course = course;
    _this.chapter = chapter;
    // 调用 list()方法
    //初始化第一页
    _this.list(1);
    //sidebar激活样式方法一
    this.$parent.activeSidebar("business-course-sidebar");
  },
  methods: {
    add() {
      let _this = this;
      _this.section = {};
      $("#forn-modal").modal("show");
      // $(".modal").modal("hide");

    },
    edit(section) {
      let _this = this;
      _this.section = section;
      $("#forn-modal").modal("show");

    },
    list(page) {
      let _this = this;
      // loading显示
      Loading.show();
      // 获取list 从后台获取sql数据
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/list',
          {
            // 设置页数 (前端传进来的参数)
            page: page,
            // $refs.参数 意思是获取子组件 就是后面的参数
            //pagination是外部文件 在上面有引入
            size: _this.$refs.pagination.size,
            courseId: _this.course.id,
            chapterId: _this.chapter.id
          }).then((response) => {
        // loading隐藏
        Loading.hide();
        // 数据存储在response
        console.log("查询小节列表结果:", response);
        // 就数据放进前端data(双面数据) 渲染数据
        //获取到数据 存进data ,data点出list(记录)  渲染数据
        // resp指的是ResponseDto
        let resp = response.data;
        _this.sections = resp.content.list;
        //获取到数据 存进data ,data点出total(总页数) 渲染数据
        //前端获取到传来的 泛型数据:content,然后点出里面的参数
        _this.$refs.pagination.render(page, resp.content.total);
      })
    },
    // save方法:表单新增前端核心代码
    save(page) {
      let _this = this;
      //保存校验
      if (1 != 1
          || !Validator.require(_this.section.title, "标题")
          || !Validator.length(_this.section.title, "标题", 1, 50)
          || !Validator.length(_this.section.video, "视频", 1, 200)
      ) {
        return;
      }
      _this.section.courseId = _this.course.id;
      _this.section.chapterId = _this.chapter.id;
      // loading显示
      Loading.show();
      // 获取list 从后台获取sql数据
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/save',
          //参数
          _this.section
      ).then((response) => {
        // loading隐藏
        Loading.hide();
        // 数据存储在response
        console.log("保存小节列表结果:", response);
        // resp指的是ResponseDto
        let resp = response.data;
        if (resp.success) {
          //如果保存成功的话:resp.success 就关闭模态框 并且刷新页面到第一页使他更新数据
          $("#forn-modal").modal("hide");
          _this.list(1);
          Toast.success("保存成功!")
        }
      })
    },
    del(id) {
      let _this = this;

      Confirm.show("删除小节后不可恢复,确认删除?!", function () {
        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/section/delete/' + id).then((response) => {
          // loading显示
          Loading.hide();
          console.log("删除小节列表结果:", response);
          let resp = response.data;
          if (resp.success) {
            _this.list(1);
            Toast.success("删除成功!")
          }
        })
      });
    },
    /**
     * 显示图片or视频
     * */
    afterUpload(resp) {
      let _this = this;
      //resp是后台return回来的数据 resp.content则是图片的地址!!
      let video = resp.content.path;
      // 再把image赋值给前端teacher的image变量里
      _this.section.video = video;
      _this.getTime();
    },
    /**
     * 获取时长
     */
    getTime() {
      let _this = this;
      setTimeout(function () {
        //原生document获取id
        let ele = document.getElementById("video");
        //原生带有duration这个方法,是获取视频的时长,再传入10,进行10进制
        _this.section.time = parseInt(ele.duration, 10);
      }, 1000);
    },
  }
}
</script>

<style scoped>
video {
  width: 100%;
  height: auto;
  margin-top: 10px;
}
</style>
