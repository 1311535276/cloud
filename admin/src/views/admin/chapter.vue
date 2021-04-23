<template>
    <div>
        <h4 class="lighter">
            <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
            <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
        </h4>
        <hr>

        <p>
            <router-link to="/business/course" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-arrow-left"></i>
                返回课程
            </router-link>
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
                <th>名称</th>
                <th>操作</th>

            </tr>
            </thead>

            <tbody>
            <tr v-for="chapter in chapters">
                <td>{{chapter.id}}</td>
                <td>{{chapter.name}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="toSection(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                            小节
                        </button>&nbsp;
                        <button v-on:click="edit(chapter)" class="btn btn-white btn-xs btn-info btn-round">
                            编辑
                        </button>&nbsp;
                        <button v-on:click="del(chapter.id)" class="btn btn-white btn-xs btn-warning btn-round">
                            删除
                        </button>
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
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label" >名称</label>
                                <div class="col-sm-10">
                                    <input v-model="chapter.name" type="text" class="form-control"  placeholder="名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">课程ID</label>
                                <div class="col-sm-10">
                                    <p class="form-control-static">{{course.name}}</p>
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

    export default {
        //引入外部文件二
        components: {Pagination},
        name: "chapter",
        data:function(){
            //数据绑定写在这里
            return {
                chapter:{},
                chapters:[],
                course:{},
            }
        },
        mounted: function () {
            let _this=this;
            _this.$refs.pagination.size = 5;
            // let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
           //bug记录处 全局变量没有效果
            let course = SessionStorage.get("course") || {};
            if (Tool.isEmpty(course)) {
                _this.$router.push("/welcome");
            }

            _this.course = course;
            // 调用 list()方法
            //初始化第一页
            _this.list(1);
            //sidebar激活样式方法一
            this.$parent.activeSidebar("business-course-sidebar");

        },
        methods: {
            add(){
                let _this=this;
                _this.chapter= {};
                $("#forn-modal").modal("show");
                // $(".modal").modal("hide");
            },
            edit(chapter){
                let _this=this;
                _this.chapter =chapter;
                $("#forn-modal").modal("show");

            },
            // 列表查询
            list(page){
                let _this=this;
                // loading显示
                Loading.show();
                // 获取list 从后台获取sql数据
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/list',
                    {
                        // 设置页数 (前端传进来的参数)
                        page: page,
                        // $refs.参数 意思是获取子组件 就是后面的参数
                        //pagination是外部文件 在上面有引入
                        size: _this.$refs.pagination.size,
                        courseId:_this.course.id
                    }).then((response)=>{
                    // loading隐藏
                    Loading.hide();
                    // 数据存储在response
                    console.log("查询大章列表结果:",response);
                    // 就数据放进前端data(双面数据) 渲染数据
                    //获取到数据 存进data ,data点出list(记录)  渲染数据
                    // resp指的是ResponseDto
                  let resp= response.data;
                    _this.chapters =resp.content.list;
                    //获取到数据 存进data ,data点出total(总页数) 渲染数据
                    //前端获取到传来的 泛型数据:content,然后点出里面的参数
                    _this.$refs.pagination.render(page,resp.content.total);
                })
            },
            // save方法:表单新增前端核心代码
            save(page){
                let _this=this;
                // loading显示
                //保存校验
                if(!Validator.require(_this.chapter.name,"名称")
                ||!Validator.length(_this.chapter.courseId,"课程ID",1,8)){
                    return;
                }
                _this.chapter.courseId= this.course.id;
                Loading.show();
                // 获取list 从后台获取sql数据
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/save',
                       //参数
                       _this.chapter
                    ).then((response)=>{
                    // loading隐藏
                    Loading.hide();
                    // 数据存储在response
                    console.log("保存大章列表结果:",response);
                    // resp指的是ResponseDto
                    let resp= response.data;
                    if(resp.success){
                        //如果保存成功的话:resp.success 就关闭模态框 并且刷新页面到第一页使他更新数据
                        $("#forn-modal").modal("hide");
                        _this.list(1);
                        Toast.success("保存成功!")
                    }
                })
            },
            del(id){
                let _this=this;

                Confirm.show("删除大章后不可恢复,确认删除?!",function(){
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+'/business/admin/chapter/delete/'+id).then((response)=>{
                        // loading显示
                        Loading.hide();
                        console.log("删除大章列表结果:",response);
                        let resp=response.data;
                        if(resp.success){
                            _this.list(1);
                            Toast.success("删除成功!")
                        }
                    })
            });
            },
            /**
             * 点击【小节】
             */
            toSection(chapter) {
                let _this = this;
                SessionStorage.set(SESSION_KEY_CHAPTER, chapter);
                _this.$router.push("/business/section");
            }
        }
    }
</script>