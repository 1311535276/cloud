<template>
  <div>
    <p>
      <!--<button v-on:click="add()" class="btn btn-white btn-default btn-round">-->
      <!--  <i class="ace-icon fa fa-edit"></i>-->
      <!--  新增-->
      <!--</button>-->
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
        <th>id</th>
        <th>相对路径</th>
        <th>文件名</th>
        <th>后缀</th>
        <th>大小</th>
        <th>用途</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="file in files">
        <td>{{file.id}}</td>
        <td>{{file.path}}</td>
        <td>{{file.name}}</td>
        <td>{{file.suffix}}</td>
        <td>{{file.size | formatFileSize}}</td>
        <td>{{FILE_USE | optionKV(file.use)}}</td>
      </tr>
      </tbody>
    </table>
    <!--模态框-->
    <!--<div id="forn-modal" class="modal fade" tabindex="-1" role="dialog">-->
    <!--  <div class="modal-dialog" role="document">-->
    <!--    <div class="modal-content">-->
    <!--      <div class="modal-header">-->
    <!--        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span-->
    <!--            aria-hidden="true">&times;</span></button>-->
    <!--        <h4 class="modal-title">表单</h4>-->
    <!--      </div>-->
    <!--      <div class="modal-body">-->
    <!--        <form class="form-horizontal">-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">相对路径</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <input v-model="file.path" class="form-control">-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">文件名</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <input v-model="file.name" class="form-control">-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">后缀</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <input v-model="file.suffix" class="form-control">-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">大小</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <input v-model="file.size" class="form-control">-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">用途</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <select v-model="file.use" class="form-control">-->
    <!--                <option v-for="o in FILE_USE" v-bind:value="o.key">{{ o.value }}</option>-->
    <!--              </select>-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">已上传分片</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <input v-model="file.shardIndex" class="form-control">-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">分片大小</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <input v-model="file.shardSize" class="form-control">-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">分片总数</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <input v-model="file.shardTotal" class="form-control">-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">文件标识</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <input v-model="file.key" class="form-control">-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <div class="form-group">-->
    <!--            <label class="col-sm-2 control-label">vod</label>-->
    <!--            <div class="col-sm-10">-->
    <!--              <input v-model="file.vod" class="form-control">-->
    <!--            </div>-->
    <!--          </div>-->
    <!--        </form>-->

    <!--      </div>-->
    <!--      <div class="modal-footer">-->
    <!--        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>-->
    <!--        <button type="button" class="btn btn-primary" v-on:click="save()">保存</button>-->
    <!--      </div>-->
    <!--    </div>&lt;!&ndash; /.modal-content &ndash;&gt;-->
    <!--  </div>&lt;!&ndash; /.modal-dialog &ndash;&gt;-->
    <!--</div>&lt;!&ndash; /.modal &ndash;&gt;-->
  </div>
</template>

<script>
//引入外部文件一
import Pagination from "../../components/pagination"

export default {
  //引入外部文件二
  components: {Pagination},
  name: "file-file",
  data: function () {
    //数据绑定写在这里
    return {
      file: {},
      files: [],
      // FILE_USE: FILE_USE,
    }
  },
  mounted: function () {

    let _this = this;
    _this.$refs.pagination.size = 5;
    // 调用 list()方法
    //初始化第一页
    _this.list(1);
    //sidebar激活样式方法一
    // this.$parent.activeSidebar("file-sidebar");
  },
  methods: {
    add() {
      let _this = this;
      _this.file = {};
      $("#forn-modal").modal("show");
      // $(".modal").modal("hide");

    },
    edit(file) {
      let _this = this;
      _this.file = file;
      $("#forn-modal").modal("show");

    },
    list(page) {
      let _this = this;
      // loading显示
      Loading.show();
      // 获取list 从后台获取sql数据
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/file/list',
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
        console.log("查询文件列表结果:", response);
        // 就数据放进前端data(双面数据) 渲染数据
        //获取到数据 存进data ,data点出list(记录)  渲染数据
        // resp指的是ResponseDto
        let resp = response.data;
        _this.files = resp.content.list;
        //获取到数据 存进data ,data点出total(总页数) 渲染数据
        //前端获取到传来的 泛型数据:content,然后点出里面的参数
        _this.$refs.pagination.render(page, resp.content.total);
      })
    },
    // save方法:表单新增前端核心代码
    save() {
      let _this = this;
      //保存校验
      if (1 != 1
          || !Validator.require(_this.file.path, "相对路径")
          || !Validator.length(_this.file.path, "相对路径", 1, 100)
          || !Validator.length(_this.file.name, "文件名", 1, 100)
          || !Validator.length(_this.file.suffix, "后缀", 1, 10)
          || !Validator.length(_this.file.key, "文件标识", 1, 32)
      ) {
        return;
      }
      // loading显示
      Loading.show();
      // 获取list 从后台获取sql数据
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/file/save',
          //参数
          _this.file
      ).then((response) => {
        // loading隐藏
        Loading.hide();
        // 数据存储在response
        console.log("保存文件列表结果:", response);
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

      Confirm.show("删除文件后不可恢复,确认删除?!", function () {
        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/file/admin/file/delete/' + id).then((response) => {
          // loading显示
          Loading.hide();
          console.log("删除文件列表结果:", response);
          let resp = response.data;
          if (resp.success) {
            _this.list(1);
            Toast.success("删除成功!")
          }
        })
      });

    }
  }
}
</script>
