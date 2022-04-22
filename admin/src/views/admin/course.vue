<template>
  <div>
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

    <div class="row">
      <div v-for="course in courses" class="col-md-3">
        <div class="thumbnail search-thumbnail">
          <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg"/>
          <img v-show="course.image" class="media-object" v-bind:src="course.image"/>
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-primary info-label">
                {{ COURSE_LEVEL | optionKV(course.level) }}
              </span>
              <span class="pull-right label label-primary info-label">
                {{ COURSE_CHARGE | optionKV(course.charge) }}
              </span>
              <span class="pull-right label label-primary info-label">
                {{ COURSE_STATUS | optionKV(course.status) }}
              </span>
            </div>
            <h3 class="search-title">
              <a href="#" class="blue">{{ course.name }}</a>
            </h3>
            <!--讲师头像的显示前端代码-->
            <div v-for="teacher in teachers.filter(t=>{return t.id===course.teacherId})"
                 class="profile-activity clearfix">
              <div>
                <img v-show="!teacher.image" class="pull-left" src="/ace/assets/images/avatars/avatar5.png">
                <img v-show="teacher.image" class="pull-left" v-bind:src="teacher.image">
                <a class="user" href="#"> {{ teacher.name }} </a>
                <br>
                {{ teacher.position }}
              </div>
            </div>


            <p>
              <span class="blue bolder bigger-150"><i class="fa fa-rmb"></i>{{ course.price }}&nbsp;</span>&nbsp;
            </p>
            <p>{{ course.summary }}</p>
            <p>
              <span class="badge badge-info">{{ course.id }}</span>
              <span class="badge badge-info">排序：{{ course.sort }}</span>
              <span class="badge badge-info">{{ course.time | formatSecond }}</span>
            </p>
            <p>
              <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                大章
              </button>&nbsp;
              <!--<button v-on:click="editContent(course)" class="btn btn-white btn-xs btn-info btn-round">-->
              <button v-on:click="toContent(course)" class="btn btn-white btn-xs btn-info btn-round">
              内容
              </button>&nbsp;
              <button v-on:click="openSortModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                排序
              </button>&nbsp;
              <button v-on:click="edit(course)" class="btn btn-white btn-xs btn-info btn-round">
                编辑
              </button>&nbsp;
              <button v-on:click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                删除
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
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
                <label class="col-sm-2 control-label">
                  分类
                </label>
                <div class="col-sm-10">
                  <ul id="tree" class="ztree"></ul>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">封面</label>
                <div class="col-sm-10">
                  <!--<file v-bind:input-id="'image-upload'"-->
                  <!--      v-bind:text="'上传封面'"-->
                  <!--      v-bind:suffixs="['jpg', 'jpeg', 'png']"-->
                  <!--      v-bind:use="FILE_USE.COURSE.key"-->
                  <!--      v-bind:after-upload="afterUpload">-->
                  <!--</file>-->
                  <big-file v-bind:input-id="'image-upload'"
                            v-bind:text="'上传封面'"
                            v-bind:suffixs="['jpg', 'jpeg', 'png']"
                            v-bind:use="FILE_USE.COURSE.key"
                            v-bind:after-upload="afterUpload"></big-file>
                  <!--图片显示-->
                  <div v-show="course.image" class="row">
                    <div class="col-md-6">
                      <img v-bind:src="course.image" class="img-responsive">
                    </div>
                    <!--<input v-model="course.image" class="form-control">-->
                  </div>
                </div>
              </div>
                <div class="form-group">
                  <div class="form-group">
                    <label class="col-sm-2 control-label">
                      名称
                    </label>
                    <div class="col-sm-10">
                      <input v-model="course.name" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">讲师</label>
                    <div class="col-sm-10">
                      <select v-model="course.teacherId" class="form-control">
                        <option v-for="o in teachers" v-bind:value="o.id">{{ o.name }}</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">概述</label>
                    <div class="col-sm-10">
                      <input v-model="course.summary" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">时长</label>
                    <div class="col-sm-10">
                      <input v-model="course.time" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">价格（元）</label>
                    <div class="col-sm-10">
                      <input v-model="course.price" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">级别</label>
                    <div class="col-sm-10">
                      <select v-model="course.level" class="form-control">
                        <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{ o.value }}</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">收费</label>
                    <div class="col-sm-10">
                      <select v-model="course.charge" class="form-control">
                        <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{ o.value }}</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">状态</label>
                    <div class="col-sm-10">
                      <select v-model="course.status" class="form-control">
                        <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{ o.value }}</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">报名数</label>
                    <div class="col-sm-10">
                      <input v-model="course.enroll" class="form-control">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">顺序</label>
                    <div class="col-sm-10">
                      <input v-model="course.sort" class="form-control" disabled>
                    </div>
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
    </div>
    <!-- /.modal -->


    <div id="course-content-modal" class="modal fade" tabindex="-1" role="dialog" style="overflow:auto;">
      <div class="modal-dialog modal-1g" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
            <h4 class="modal-title">内容编辑</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <div class="col-lg-12">
                  {{ saveContentLabel }}
                </div>
              </div>
              <div class="form-group">
                <div class="col-lg-12">
                  <div id="content"></div>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="saveContent()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div>
      </div>
    </div>

    <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">排序</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="control-label col-lg-3">
                  当前排序
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                </div>
              </div>
              <div class="form-group">
                <label class="control-label col-lg-3">
                  新排序
                </label>
                <div class="col-lg-9">
                  <input class="form-control" v-model="sort.newSort" name="newSort">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              取消
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="updateSort()">
              <i class="ace-icon fa fa-plus blue"></i>
              更新排序
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

  </div>
</template>

<script>
//引入外部文件一
import Pagination from "../../components/pagination";
// import File from "../../components/file";
import BigFile from "../../components/big-file";

export default {
  //引入外部文件二
  components: {Pagination, BigFile},
  name: "business-course",
  data: function () {
    //数据绑定写在这里
    return {
      course: {},
      courses: [],
      COURSE_LEVEL: COURSE_LEVEL,
      COURSE_CHARGE: COURSE_CHARGE,
      COURSE_STATUS: COURSE_STATUS,
      categorys: [],
      FILE_USE: FILE_USE,
      tree: {},
      saveContentLabel: "",
      sort: {
        id: "",
        oldSort: 0,
        newSort: 0
      },
      teachers: [],
    }
  },
  mounted: function () {
    let _this = this;
    // SessionStorage.set(SESSION_KEY_COURSE, course);
    _this.$refs.pagination.size = 4;
    _this.allCategory();
    //加载讲师
    _this.allTeacher()

    // 调用 list()方法
    //初始化第一页
    _this.list(1);
    //sidebar激活样式方法一
    // this.$parent.activeSidebar("business-sidebar");
  },
  methods: {
    /**
     * 点击新增
     * */
    add() {
      let _this = this;
      _this.course = {
        sort: _this.$refs.pagination.total + 1
      };

      this.tree.checkAllNodes(false);
      $("#forn-modal").modal("show");
      // $(".modal").modal("hide");

    },
    /**
     * 点击【编辑】
     * */
    edit(course) {
      let _this = this;
      _this.course = course;
      this.listCategory(course.id);
      $("#forn-modal").modal("show");

    },
    list(page) {
      let _this = this;
      // loading显示
      Loading.show();
      // 获取list 从后台获取sql数据
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list',
          {
            // 设置页数 (前端传进来的参数)
            page: page,
            // $refs.参数 意思是获取子组件 就是后面的参数
            //pagination是外部文件 在上面有引入
            size: _this.$refs.pagination.size,
          }).then((response) => {
        // loading隐藏
        Loading.hide();
        // 数据存储在response
        console.log("查询课程列表结果:", response);
        // 就数据放进前端data(双面数据) 渲染数据
        //获取到数据 存进data ,data点出list(记录)  渲染数据
        // resp指的是ResponseDto
        let resp = response.data;
        _this.courses = resp.content.list;
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
          || !Validator.require(_this.course.name, "名称")
          || !Validator.length(_this.course.name, "名称", 1, 50)
          || !Validator.length(_this.course.summary, "概述", 1, 2000)
          || !Validator.length(_this.course.image, "封面", 1, 100)
      ) {
        return;
      }

      let categorys = _this.tree.getCheckedNodes();

      if (Tool.isEmpty(categorys)) {
        Toast.warning("請選擇分類 !")
        return;
      }
      console.log(categorys);
      this.course.categorys = categorys;
      // loading显示
      Loading.show();
      // 保存ajax
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save',
          //参数
          _this.course
      ).then((response) => {
        // loading隐藏
        Loading.hide();
        // 数据存储在response
        console.log("保存课程列表结果:", response);
        // resp指的是ResponseDto
        let resp = response.data;
        if (resp.success) {
          //如果保存成功的话:resp.success 就关闭模态框 并且刷新页面到第一页使他更新数据
          $("#forn-modal").modal("hide");
          _this.list(1);
          Toast.success("保存成功!")
        }else {
          Toast.warning(resp.message);
        }
      })
    },
    del(id) {
      let _this = this;

      Confirm.show("删除课程后不可恢复,确认删除?!", function () {
        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course/delete/' + id).then((response) => {
          // loading显示
          Loading.hide();
          console.log("删除课程列表结果:", response);
          let resp = response.data;
          if (resp.success) {
            _this.list(1);
            Toast.success("删除成功!")
          }
        })
      });
    },

    /**
     * 点击【大章】
     */
    toChapter(course) {
      let _this = this;
      SessionStorage.set(SESSION_KEY_COURSE, course);
      _this.$router.push("/business/chapter");
    },

    /**
     * 点击【内容】
     */
    toContent(course) {
      let _this = this;
      SessionStorage.set(SESSION_KEY_COURSE, course);
      _this.$router.push("/business/content");
    },
    // 这里 打开内容编辑
    //废弃了 放到content.vue里
    editContent(course) {
      let _this = this;
      let id = course.id;
      this.course = course;
      $("#content").summernote({
        focus: true,
        height: 300
      });
      //先清空历史文本
      $("#content").summernote('code', '');
      _this.saveContentLabel = "",
          Loading.show();
      _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/admin/course/find-content/' + id).then((response) => {
        Loading.hide();
        let resp = response.data;
        if (resp.success) {

          if (resp.content) {
            $("#course-content-modal").modal({backdrop: 'static', keyboard: false});
            $("#content").summernote('code', resp.content.content);
          }
          // 定时自动保存
          let saveContentInterval = setInterval(function () {
            _this.saveContent();
          }, 5000);
          //关闭内容框时,清空自动保存任务
          $('#course-content-modal').on('hidden.bs.modal', function (e) {
            clearInterval(saveContentInterval);
          })
        } else {
          Toast.warning(resp.message);
        }
      });
    },
    /**
     * 保存内容
     */
    saveContent() {
      let _this = this;
      let content = $("#content").summernote("code");
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content', {
        id: _this.course.id,
        content: content
      }).then((response) => {
        Loading.hide();
        let resp = response.data;
        if (resp.success) {
          // Toast.success("内容保存成功");
          let now = Tool.dateFormat("yyyy-MM-dd hh:mm:ss");
          // let now = Tool.dateFormat("mm:ss");
          _this.saveContentLabel = "最后保存时间：" + now;
        } else {
          Toast.warning(resp.message);
        }
      });
    },


    allCategory() {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all')
          .then((response) => {
            Loading.hide();
            let resp = response.data;
            _this.categorys = resp.content;

            _this.initTree();
          })
    },
    initTree() {
      let _this = this;
      var setting = {
        check: {
          enable: true
        },
        data: {
          simpleData: {
            idKey: "id",
            pIdKey: "parent",
            rootPId: "0000000o",
            enable: true
          }
        }
      };

      var zNodes = _this.categorys;

      _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);
      // tree.getCheckedNodes();

      // 展开所有的节点
      // _this.tree.expandAll(true);
    },
    /**
     * 查找课程下所有分类
     * @param courseId
     */
    listCategory(courseId) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list-category/' + courseId).then((res) => {
        Loading.hide();
        console.log("查找课程下所有分类结果：", res);
        let response = res.data;
        let categorys = response.content;

        // 勾选查询到的分类
        _this.tree.checkAllNodes(false);
        for (let i = 0; i < categorys.length; i++) {
          let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
          _this.tree.checkNode(node, true);
        }
      })
    },
    openSortModal(course) {
      let _this = this;
      _this.sort = {
        id: course.id,
        oldSort: course.sort,
        newSort: course.sort
      };
      $("#course-sort-modal").modal("show");
    },

    /**
     * 排序
     */
    updateSort() {
      let _this = this;
      if (_this.sort.newSort === _this.sort.oldSort) {
        Toast.warning("排序没有变化");
        return;
      }
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/sort", _this.sort).then((res) => {
        let response = res.data;

        if (response.success) {
          Toast.success("更新排序成功");
          $("#course-sort-modal").modal("hide");
          _this.list(1);
        } else {
          Toast.error("更新排序失败");
        }
      });
    },
    allTeacher() {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/all').then((response) => {
        Loading.hide();
        let resp = response.data;
        _this.teachers = resp.content;

        _this.initTree();
      })
    },
    afterUpload(resp) {
      let _this = this;
      //resp是后台return回来的数据 resp.content则是图片的地址!!
      let image = resp.content.path;
      // 再把image赋值给前端teacher的image变量里
      _this.course.image = image;
    },
  }
}
</script>


<style scoped>
.caption h3 {
  font-size: 20px;
}

@media (max-width: 1199px) {
  .caption h3 {
    font-size: 16px;
  }
}
</style>
