<template>
  <div>
    <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-upload"></i>
      {{text}}
    </button>
    <!--class="hidden"  这段隐藏了相关信息来的(去掉hidden就可以了)-->
    <!--<input type="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'"  ref="file"/>-->
    <input class="hidden" type="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'"  ref="file"/>

  </div>
</template>

<script>
export default {
  name: 'file',
  props: {
    text:{
      default: "上传文件"
    },
    inputId:{
      default: "file-upload"
    },
    suffixs:{
      default:[]
    },
    use: {
      default: ""
    },
    afterUpload: {
      type: Function,
      default: null
    },
  },
  data: function () {
    return {

    }
  },
  methods: {
    uploadFile() {
      let _this = this;
      let formData = new window.FormData();
      let file = _this.$refs.file.files[0];
      //判断文件格式
      //定义我需要的文件后缀
      // ["jpg", "jpeg", "png"];
      let suffixs = _this.suffixs;
      //获取到当前上传的文件名字
      let fileName = file.name;
      //切割 分割 获取当前上传文件的后缀
      let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
      //定一个检验是否通过的变量 初始值为false
      let validateSuffix = false;
      //循环数组 来判断 核对
      for (let i = 0; i < suffixs.length; i++) {
        //统一转成小写 来进行判断
        if (suffixs[i].toLowerCase() === suffix) {
          //校验通过 变量为true;
          validateSuffix = true;
          break;
        }
      }
      //判断
      if(!validateSuffix) {
        Toast.warning("文件格式不正确！只支持上传：" + suffixs.join(","));
        console.log("文件格式不正确！只支持上传：" + suffixs.join(","));
        $("#" + _this.inputId + "-input").val("");
        return;
      }
      //key："file"必须controller参数名一致
      // formData.append('file', document.querySelector('#file-upload-input').files[0]);
      formData.append('file', file);
      formData.append('use', _this.use);
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', formData).then((response) => {
        Loading.hide();
        let resp = response.data;
        //resp是后台return回来的数据
        console.log("上传文件成功:", resp);
        // console.log("image:", image);
        _this.afterUpload(resp);
        $("#" + _this.inputId + "-input").val("");


        // console.log("上传文件地址:", image);
        // 再把image赋值给前端teacher的image变量里
        // _this.teacher.image = image;
      });
    },
    selectFile () {
      let _this = this;
      $("#" + _this.inputId + "-input").trigger("click");
    },
  }
}
</script>
