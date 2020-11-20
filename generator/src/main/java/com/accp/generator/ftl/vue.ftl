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
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
              <#list fieldList as field>
                  <#if field.nameHump!="createdAt" && field.nameHump!="updatedAt">
                      <th>${field.nameCn}</th>
                  </#if>
              </#list>
                <th>操作</th>

            </tr>
            </thead>

            <tbody>
            <tr v-for="${domain} in ${domain}s">
              <#list fieldList as field>
                  <#if field.nameHump!="createdAt" && field.nameHump!="updatedAt">
                <td>{{${domain}.${field.nameHump}}}</td>
                  </#if>
              </#list>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="edit(${domain})" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button v-on:click="del(${domain}.id)" class="btn btn-xs btn-danger">
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
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                          <#list fieldList as field>
                              <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt">
                            <div class="form-group">
                            <label class="col-sm-2 control-label" >${field.nameCn}</label>
                            <div class="col-sm-10">
                              <input v-model="${domain}.${field.nameHump}" type="text" class="form-control">
                            </div>
                            </div>
                            </#if>
                          </#list>
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
        name: "${domain}",
        data:function(){
            //数据绑定写在这里
            return {
                ${domain}:{},
                ${domain}s:[]
            }
        },
        mounted: function () {

            let _this=this;
            _this.$refs.pagination.size=5;
            // 调用 list()方法
            //初始化第一页
            _this.list(1);
            //sidebar激活样式方法一
            // this.$parent.activeSidebar("${module}-sidebar");
        },
        methods: {
            add(){
                let _this=this;
                _this.${domain}= {};
                $("#forn-modal").modal("show");
                // $(".modal").modal("hide");

            },
            edit(${domain}){
                let _this=this;
                _this.${domain} =${domain};
                $("#forn-modal").modal("show");

            },
            list(page){
                let _this=this;
                // loading显示
                Loading.show();
                // 获取list 从后台获取sql数据
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/${module}/admin/${domain}/list',
                    {
                        // 设置页数 (前端传进来的参数)
                        page: page,
                        // $refs.参数 意思是获取子组件 就是后面的参数
                        //pagination是外部文件 在上面有引入
                        size: _this.$refs.pagination.size,
                    }).then((response)=>{
                    // loading隐藏
                    Loading.hide();
                    // 数据存储在response
                    console.log("查询${tableNameCn}列表结果:",response);
                    // 就数据放进前端data(双面数据) 渲染数据
                    //获取到数据 存进data ,data点出list(记录)  渲染数据
                    // resp指的是ResponseDto
                  let resp= response.data;
                    _this.${domain}s =resp.content.list;
                    //获取到数据 存进data ,data点出total(总页数) 渲染数据
                    //前端获取到传来的 泛型数据:content,然后点出里面的参数
                    _this.$refs.pagination.render(page,resp.content.total);
                })
            },
            // save方法:表单新增前端核心代码
            save(page){
                let _this=this;
                   //保存校验
                if(1 != 1
                    <#list fieldList as field>
                        <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
                    <#if !field.nullAble>
                    || !Validator.require(_this.${domain}.${field.nameBigHump},"${field.nameCn}")
                </#if>
                <#if (field.length > 0)>
               || !Validator.length(_this.${domain}.${field.nameBigHump}, "${field.nameCn}", 1, ${field.length})
                </#if>
                    </#if>
                </#list>
            ){
                    return;
                }
                // loading显示
                Loading.show();
                // 获取list 从后台获取sql数据
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/${module}/admin/${domain}/save',
                       //参数
                       _this.${domain}
                    ).then((response)=>{
                    // loading隐藏
                    Loading.hide();
                    // 数据存储在response
                    console.log("保存${tableNameCn}列表结果:",response);
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

                Confirm.show("删除${tableNameCn}后不可恢复,确认删除?!",function(){
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+'/${module}/admin/${domain}/delete/'+id).then((response)=>{
                        // loading显示
                        Loading.hide();
                        console.log("删除${tableNameCn}列表结果:",response);
                        let resp=response.data;
                        if(resp.success){
                            _this.list(1);
                            Toast.success("删除成功!")
                        }
                    })
            });

            }
        }
    }
</script>