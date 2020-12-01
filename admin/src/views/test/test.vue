<template>
    <div>
        <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>名称</th>
                <th>课程ID</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="chapter in chapters">
                <td>{{test.id}}</td>
                <td>{{test.name}}</td>
                <td>{{test.courseId}}</td>

            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    //引入外部文件一
    import Pagination from "../../components/pagination"

    export default {
        //引入外部文件二
        components: {Pagination},
        name: "test",
        data:function(){
            //数据绑定写在这里
            return {
                chapter:{},
                chapters:[]
            }
        },
        mounted: function () {

            let _this=this;
            _this.$refs.pagination.size=5;
            // 调用 list()方法
            //初始化第一页
            _this.list(1);
        },
        methods: {
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

        }
    }
</script>