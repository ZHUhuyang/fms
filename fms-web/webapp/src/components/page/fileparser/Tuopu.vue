<template>
    <div>
        <div>
            <Row>
                <router-link to="/tuopu"> <Button class="tableBtn" >拓扑图</Button></router-link>
                <router-link to="/tuopuManage"> <Button class="tableBtn" >拓扑图管理</Button></router-link>
                <router-link to="/control"> <Button class="tableBtn" >控件管理</Button></router-link>
            </Row>
        </div>
        <div class="layout">
            <div id="editor" data-options="region:'center'"></div>
            <div style="z-index:998; position:absolute;margin-top:6px;margin-left:1000px;">
                <label>图片名称：</label>
                <input id="saveName" name="saveName" style="width:75px"/>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            graph: null,
            loc: [],
        }
    },
    mounted() {
        let _this=this;
        $('#editor').graphEditor({
            images: [{
                name: 'Custom Elements',
                images: this.loc
            }],
            callback: function (editor) {
                _this.init(editor.graph, editor);
                _this.showPic(editor);
            }
        });
    },
    created() {
        this.getData();
    },


    methods: {
        showPic(editor) {
            this.graph = editor.graph;
            this.$axios.post('mvc/picture/showPicture').then(function (res) {  //接口返回数据
                graph.clear();
                graph.parseJSON(res);
            }).catch(function (error) {
                console.log(error);
            });
        },

        //显示控件
        getData(){
            this.$axios.post('mvc/control/getList').then(function (res) {  //接口返回数据
                let loc = res.data.split(",");
                console.log(loc);
            }).catch(function (error) {
                console.log(error);
            });
        },


        init(graph, editor)
        {
            editor.toolbox.hideDefaultGroups();
            editor.toolbox.hideButtonBar();
        }
    }
}
</script>
