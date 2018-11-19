<template>
        <div class="container">
            <Button @click="handleAdd">新增</Button>
            <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)" border class="table" ref="multipleTable" >
                <el-table-column prop="ip" align="center" label="ip" width="120">
                </el-table-column>
                <el-table-column prop="userName" align="center" label="用户名" width="120">
                </el-table-column>
                <el-table-column prop="password" align="center" label="密码" width="120">
                </el-table-column>
                <el-table-column prop="port" align="center" label="端口号" width="120">
                </el-table-column>
                <el-table-column prop="path" align="center" label="下载路径" width="120">
                </el-table-column>
                <el-table-column prop="format" align="center" label="是否格式化" width="120">
                </el-table-column>
                <el-table-column label="操作" align="center" width="160">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

                <el-dialog title="新增" :visible.sync="addVisible" width="40%">
                    <el-form ref="form" :model="form" label-width="100px">
                        <el-form-item label="ip：">
                            <el-input v-model="form.ip"></el-input>
                        </el-form-item>
                        <el-form-item label="用户名：">
                            <el-input v-model="form.userName"></el-input>
                        </el-form-item>
                        <el-form-item label="密码：">
                            <el-input v-model="form.password"></el-input>
                        </el-form-item>
                        <el-form-item label="端口号：">
                            <el-input v-model="form.port"></el-input>
                        </el-form-item>
                        <el-form-item label="下载路径：">
                            <el-input v-model="form.path"></el-input>
                        </el-form-item>
                        <el-form-item label="是否格式化：" prop="selects">
                            <el-select v-model="select_cate" placeholder="请选择" class="handle-select mr10">
                                <el-option key="1" label="格式化" value="格式化"></el-option>
                                <el-option key="2" label="非格式化" value="非格式化"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAdd">确 定</el-button>
            </span>
                </el-dialog>

                <el-dialog title="编辑" :visible.sync="editVisible" width="40%">
                    <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="ip：">
                        <el-input v-model="form.ip"></el-input>
                    </el-form-item>
                    <el-form-item label="用户名：">
                        <el-input v-model="form.userName"></el-input>
                    </el-form-item>
                    <el-form-item label="密码：">
                        <el-input v-model="form.password"></el-input>
                    </el-form-item>
                    <el-form-item label="端口号：">
                        <el-input v-model="form.port"></el-input>
                    </el-form-item>
                    <el-form-item label="下载路径：">
                        <el-input v-model="form.path"></el-input>
                    </el-form-item>
                        <el-form-item label="是否格式化：" prop="selects">
                            <el-select v-model="select_cate" placeholder="请选择" class="handle-select mr10">
                                <el-option key="1" label="格式化" value="格式化"></el-option>
                                <el-option key="2" label="非格式化" value="非格式化"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
                </el-dialog>

                <!-- 删除提示框 -->
                <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
                    <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
                    <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
                </el-dialog>

            <div class="pagination">
                <el-pagination
                    background
                    layout="prev, pager, next"
                    :total="tableData.length"
                    :page-size="pageSize"
                    :current-page="currentPage"
                    @current-change="handleCurrentChange">
                </el-pagination>
            </div>
        </div>


</template>

<script>
    export default {
        data() {
            return {
                select_cate: '',
                editVisible: false,
                delVisible: false,
                pageSize: 10,
                currentPage: 1,
                tableData: [],
                cur_page: 1,
                addVisible: false,
                form: {
                    ip: '',
                    userName: '',
                    password: '',
                    port: '',
                    path: ''
                },
                idx: -1
            }
        },
        created() {
            this.getData();
        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
            getData() {
                let that = this;
                this.$axios.post('mvc/fileInput/getList').then(function (res) {
                    that.tableData = res.data;
                }).catch(function (error) {
                    console.log(error);
                });
            },


            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    id:item.id,
                    ip: item.ip,
                    userName: item.userName,
                    password: item.password,
                    port: item.port,
                    path: item.path
                };
                this.editVisible = true;
            },
            saveAdd() {
                this.$axios.post('mvc/fileInput/add',{
                    //params: {
                        ip: this.form.ip,
                        userName: this.form.userName,
                        password: this.form.password,
                        port: this.form.port,
                        path: this.form.path
            //}
           })
            .then(function (res) {
                console.log(res);
            })
            .catch(function (err) {
                console.log(err);
            });
                this.addVisible = false;
                this.getData();
            },


            saveEdit() {
                this.$axios.post('mvc/fileInput/update',{
                    // params: {
                        id: this.form.id,
                        ip: this.form.ip,
                        userName: this.form.userName,
                        password: this.form.password,
                        port: this.form.port,
                        path: this.form.path
                    // }
                })
                    .then(function (res) {
                        console.log(res);
                    })
                    .catch(function (err) {
                        console.log(err);
                    });
                this.editVisible = false;
                this.getData();
            },


            handleAdd() {
                this.addVisible = true;
            },


            handleDelete(index, row) {
                this.idx = index;
                this.delVisible = true;
            },

            // 确定删除
            deleteRow() {
                this.$axios.post('mvc/fileInput/delete', {id: this.tableData[this.idx].id})
                    .then(function (response) {
                        console.log(response);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                this.$message.success('删除成功');
                this.getData();
                this.delVisible = false;
            },

            handleCurrentChange(val) {
                this.currentPage = val;
            }
        }
    }
</script>
