<template>
    <div>
            <Row>
                <router-link to="/tuopu"> <Button class="tableBtn" >拓扑图</Button></router-link>
                <router-link to="/tuopuManage"> <Button class="tableBtn" >拓扑图管理</Button></router-link>
                <router-link to="/control"> <Button class="tableBtn" >控件管理</Button></router-link>
            </Row>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="80px" >
                    <el-form-item label="控件名称">
                        <el-input v-model="form.name" style="width:200px;"></el-input>
                    </el-form-item>
                    <el-form-item label="控件类型" label-width="80px">
                        <el-select v-model="form.type" placeholder="请选择" style="width:200px;">
                            <el-option key="bbk" label="网络" value="bbk"></el-option>
                            <el-option key="xtc" label="硬件" value="xtc"></el-option>
                            <el-option key="imoo" label="区块" value="imoo"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="控件上传" label-width="80px">
                    <el-upload
                        class="upload-demo"
                        ref="upload"
                        action=""
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        multiple
                        :limit="3"
                        :on-exceed="handleExceed"
                        :auto-upload="false"
                        :file-list="fileList">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="cancelCrop">取 消</el-button>
                        <el-button type="primary" @click="onSubmit">提交</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
                        export default {
                            data() {
                                return {
                                    form: {
                                        name: '',
                                        type: ''
                                    },
                                    fileList: []
                                };
                            },
                            methods: {
                                onSubmit() {
                                    this.$axios.post("mvc/control/add", {
                                        name: this.form.name,
                                        type: this.form.type,
                                        fileList: this.fileList
                                    }).then(function (result) {
                                        console.log(result);
                                    })
                                },

                                handleRemove(file, fileList) {
                                    console.log(file, fileList);
                                },
                                handlePreview(file) {
                                    console.log(file);
                                },
                                handleExceed(files, fileList) {
                                    this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
                                },
                                beforeRemove(file, fileList) {
                                    return this.$confirm(`确定移除 ${ file.name }？`);
                                }
                            },
                        }

</script>
